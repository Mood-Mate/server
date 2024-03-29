package com.pado.socialdiary.api.diary.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import com.pado.socialdiary.api.constants.YesNoCode;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryCommentResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryCreateRequest;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryResponse;
import com.pado.socialdiary.api.moduel.diary.dto.DiaryUpdateRequest;
import com.pado.socialdiary.api.moduel.diary.entity.DiaryComment;
import com.pado.socialdiary.api.moduel.diary.mapper.DiaryMapper;
import com.pado.socialdiary.api.moduel.diary.service.DiaryService;
import com.pado.socialdiary.api.moduel.member.dto.MemberJoinRequest;
import com.pado.socialdiary.api.moduel.member.entity.GenderType;
import com.pado.socialdiary.api.moduel.member.entity.Member;
import com.pado.socialdiary.api.moduel.member.repository.MemberRepository;
import com.pado.socialdiary.api.moduel.member.service.MemberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class DiaryServiceTest {

	@Autowired
	DiaryService diaryService;
	@Autowired
	DiaryMapper diaryMapper;
	@Autowired
	MemberService memberService;
	@Autowired
	MemberRepository memberRepository;

	private final String expectedValue01 = "test01";
	private final String expectedEmailValue01 = "email01@email.com";

	@BeforeEach
	void createMember() {

		MemberJoinRequest memberJoinRequest = new MemberJoinRequest();
		memberJoinRequest.setEmail(expectedEmailValue01);
		memberJoinRequest.setPassword(expectedValue01);
		memberJoinRequest.setName(expectedValue01);
		memberJoinRequest.setGender(GenderType.ETC);
		memberJoinRequest.setYear(2000);
		memberJoinRequest.setMonth(1);
		memberJoinRequest.setDayOfMonth(1);

		memberService.join(memberJoinRequest);
	}

	@Test
	@Transactional
	@DisplayName("사진 없이 다이어리 업로드")
	void createDiaryWithoutPicture() throws IOException {
		//given
		DiaryCreateRequest diaryCreateRequest = new DiaryCreateRequest();
		diaryCreateRequest.setTitle(expectedValue01);
		diaryCreateRequest.setContents(expectedValue01);
		diaryCreateRequest.setSecret(true);

		MultipartFile multipartFile = null;

		Member member01 = memberRepository.findByEmail(expectedEmailValue01).get();

		//when
		diaryService.createDiary(member01, diaryCreateRequest, multipartFile);

		//then
		DiaryResponse diary = diaryMapper.select(member01.getMemberId(), member01.getMemberId(), LocalDateTime.now().toString()).get(0);
		assertThat(diary.getTitle()).isEqualTo(expectedValue01);
		assertThat(diary.getContents()).isEqualTo(expectedValue01);
		assertThat(diary.getPicture()).isNull();
	}

	@Test
	@Transactional
	@DisplayName("사진과 함께 다이어리 업로드")
	void createDiaryWithPicture() throws IOException {
		//given
		final String ATTACH_FILENAME = "test";
		final String ATTACH_TYPE = "jpg";
		final String ATTACH_PATH = "src/test/resources/test-image/" + ATTACH_FILENAME + "." + ATTACH_TYPE;
		FileInputStream fileInputStream = new FileInputStream(ATTACH_PATH);
		MockMultipartFile picture = new MockMultipartFile(
			expectedValue01,
			ATTACH_FILENAME + "." + ATTACH_TYPE,
			ATTACH_TYPE,
			fileInputStream
		);
		DiaryCreateRequest diaryCreateRequest = new DiaryCreateRequest();
		diaryCreateRequest.setTitle(expectedValue01);
		diaryCreateRequest.setContents(expectedValue01);
		diaryCreateRequest.setSecret(true);

		Member member01 = memberRepository.findByEmail(expectedEmailValue01).get();

		//when
		diaryService.createDiary(member01, diaryCreateRequest, picture);

		//then
		DiaryResponse diary = diaryMapper.select(member01.getMemberId(), member01.getMemberId(), LocalDateTime.now().toString()).get(0);
		assertThat(diary.getTitle()).isEqualTo(expectedValue01);
		assertThat(diary.getContents()).isEqualTo(expectedValue01);
		assertThat(diary.getDiaryPicture()).isNotNull();
	}

	@Test
	@Transactional
	@DisplayName("다이어리 수정 시 내용 업데이트, 사진 삭제")
	void editDiaryForDeletingPicture() throws IOException {
		//given
		final String ATTACH_FILENAME = "test";
		final String ATTACH_TYPE = "jpg";
		final String ATTACH_PATH = "src/test/resources/test-image/" + ATTACH_FILENAME + "." + ATTACH_TYPE;
		FileInputStream fileInputStream = new FileInputStream(ATTACH_PATH);
		MockMultipartFile picture = new MockMultipartFile(
											expectedValue01,
							  				ATTACH_FILENAME + "." + ATTACH_TYPE,
										    ATTACH_TYPE,
											fileInputStream
										);
		DiaryCreateRequest diaryCreateRequest = new DiaryCreateRequest();
		diaryCreateRequest.setTitle(expectedValue01);
		diaryCreateRequest.setContents(expectedValue01);
		diaryCreateRequest.setSecret(true);

		Member member01 = memberRepository.findByEmail(expectedEmailValue01).get();

		//when
		diaryService.createDiary(member01, diaryCreateRequest, picture);
		DiaryResponse diary = diaryMapper.select(member01.getMemberId(), member01.getMemberId(), LocalDateTime.now().toString()).get(0);

		DiaryUpdateRequest diaryUpdateRequest = new DiaryUpdateRequest();
		diaryUpdateRequest.setDiaryId(diary.getDiaryId());
		diaryUpdateRequest.setContents(expectedValue01 +1);

		diaryService.updateDiary(member01, diaryUpdateRequest, null);

		//then
		DiaryResponse updatedDiary = diaryMapper.select(member01.getMemberId(), member01.getMemberId(), LocalDateTime.now().toString()).get(0);
		assertThat(updatedDiary.getTitle()).isEqualTo(expectedValue01);
		assertThat(updatedDiary.getContents()).isEqualTo(expectedValue01 +1);
		assertThat(diary.getDiaryPicture()).isNotNull();
	}

	@Test
	@Transactional
	@DisplayName("다이어리 삭제 - 사진 및 댓글 삭제")
	void deleteDiary() throws IOException {
		//given
		final String ATTACH_FILENAME = "test";
		final String ATTACH_TYPE = "jpg";
		final String ATTACH_PATH = "src/test/resources/test-image/" + ATTACH_FILENAME + "." + ATTACH_TYPE;
		FileInputStream fileInputStream = new FileInputStream(ATTACH_PATH);
		MockMultipartFile picture = new MockMultipartFile(
			expectedValue01,
			ATTACH_FILENAME + "." + ATTACH_TYPE,
			ATTACH_TYPE,
			fileInputStream
		);
		DiaryCreateRequest diaryCreateRequest = new DiaryCreateRequest();
		diaryCreateRequest.setTitle(expectedValue01);
		diaryCreateRequest.setContents(expectedValue01);
		diaryCreateRequest.setSecret(true);

		String comment = expectedValue01;

		Member member01 = memberRepository.findByEmail(expectedEmailValue01).get();

		//when
		diaryService.createDiary(member01, diaryCreateRequest, picture);
		DiaryResponse diary = diaryMapper.select(member01.getMemberId(), member01.getMemberId(), LocalDateTime.now().toString()).get(0);

		diaryService.createDiaryComment(diary.getDiaryId(), member01, comment);
		DiaryCommentResponse findComment = diaryMapper.findDiaryCommentsByDiaryId(diary.getDiaryId()).get(0);

		diaryService.deleteDiary(diary.getDiaryId());

		//then
		DiaryComment deletedComment = diaryMapper.findDiaryCommentById(findComment.getDiaryCommentId()).get();
		assertThat(diaryMapper.getByDiaryId(diary.getDiaryId())).isNull();
		assertThat(diary.getDiaryPicture()).isNotNull();
		assertThat(deletedComment.getDelAt()).isEqualTo(YesNoCode.Y);
	}

	@Test
	@Transactional
	@DisplayName("다이어리 댓글 작성")
	void createComment() throws IOException {
		//given
		DiaryCreateRequest diaryCreateRequest = new DiaryCreateRequest();
		diaryCreateRequest.setTitle(expectedValue01);
		diaryCreateRequest.setContents(expectedValue01);
		diaryCreateRequest.setSecret(true);

		MultipartFile multipartFile = null;

		Member member01 = memberRepository.findByEmail(expectedEmailValue01).get();

		String comment = expectedValue01;

		//when
		diaryService.createDiary(member01, diaryCreateRequest, multipartFile);
		DiaryResponse diary = diaryMapper.select(member01.getMemberId(), member01.getMemberId(), LocalDateTime.now().toString()).get(0);

		diaryService.createDiaryComment(diary.getDiaryId(), member01, comment);

		//then
		DiaryCommentResponse findComment = diaryMapper.findDiaryCommentsByDiaryId(diary.getDiaryId()).get(0);
		assertThat(findComment.getContents()).isEqualTo(expectedValue01);
	}

	@Test
	@Transactional
	@DisplayName("다이어리 댓글 삭제")
	void deleteComment() throws IOException {
		//given
		DiaryCreateRequest diaryCreateRequest = new DiaryCreateRequest();
		diaryCreateRequest.setTitle(expectedValue01);
		diaryCreateRequest.setContents(expectedValue01);
		diaryCreateRequest.setSecret(true);

		MultipartFile multipartFile = null;

		Member member01 = memberRepository.findByEmail(expectedEmailValue01).get();

		String comment = expectedValue01;

		//when
		diaryService.createDiary(member01, diaryCreateRequest, multipartFile);
		DiaryResponse diary = diaryMapper.select(member01.getMemberId(), member01.getMemberId(), LocalDateTime.now().toString()).get(0);

		diaryService.createDiaryComment(diary.getDiaryId(), member01, comment);
		DiaryCommentResponse findComment = diaryMapper.findDiaryCommentsByDiaryId(diary.getDiaryId()).get(0);

		diaryService.deleteDiaryComment(findComment.getDiaryCommentId(), member01);

		//then
		DiaryComment deletedComment = diaryMapper.findDiaryCommentById(findComment.getDiaryCommentId()).get();
		assertThat(deletedComment.getDelAt()).isEqualTo(YesNoCode.Y);
	}
}
