package com.goormthon.rememberspring.diary.api.dto.response;

import com.goormthon.rememberspring.diary.domain.entity.Diary;
import com.goormthon.rememberspring.image.api.dto.response.ImageResDto;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.Builder;

@Builder
public record PublicDiaryResDto(
        Long diaryId,
        boolean isLike,
        int likeCount,
        boolean isPublic,
        String writer,
        String title,
        String createAt,
        String content,
        List<String> hashtags,
        List<ImageResDto> imageResDtoList
) {
    public static PublicDiaryResDto of(Diary diary, boolean isLike, int likeCount) {
        List<String> hashtags = diary.getDiaryHashtagMapping().stream()
                .map(diaryHashtagMapping -> diaryHashtagMapping.getHashtag().getName())
                .toList();

        List<ImageResDto> imageResDtos = diary.getImages().stream()
                .map(ImageResDto::from)
                .toList();

        return PublicDiaryResDto.builder()
                .diaryId(diary.getDiaryId())
                .isLike(isLike)
                .likeCount(likeCount)
                .isPublic(diary.isPublic())
                .writer(diary.getMember().getName())
                .title(diary.getTitle())
                .createAt(diary.getCreateAt().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")))
                .content(diary.getContent())
                .hashtags(hashtags)
                .imageResDtoList(imageResDtos)
                .build();
    }
}
