package com.podoarena.service;

import com.podoarena.dto.ConcertImgDto;
import com.podoarena.entity.ConcertImg;
import com.podoarena.repository.ConcertImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ConcertImgService {
    @Value("${concertImgLocation}")
    private String concertImgLocation;

    private final ConcertImgRepository concertImgRepository;
    private final FileService fileService;

    //저장
    public void saveConcertImg(ConcertImg concertImg, MultipartFile concertImgFile) throws Exception {
        String oriImgName = concertImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";
        if (!StringUtils.isEmpty(oriImgName)) { // 빈 문자열인지 아닌지 검사
            //빈 문자열이 아니면 업로드
            imgName = fileService.uploadFile(concertImgLocation,
                    oriImgName, concertImgFile.getBytes());
            //itemImgFile.getBytes(): 이미지 파일을 byte배열로 만들어준다.

            imgUrl = "/images/concert/" + imgName;
        }
        //DB에 insert를 하기전 유저가 직접 입력하지 못하는 값들을 개발자가 넣어준다.
        concertImg.updateConcertImg(oriImgName, imgName, imgUrl);
        concertImgRepository.save(concertImg); // insert
    }

    //가져오기
    public List<ConcertImgDto> getConcertImgDtoList(Long concertId) {
        List<ConcertImg> concertImgList = concertImgRepository.findByConcertIdOrderByIdAsc(concertId);
        List<ConcertImgDto> concertImgDtoList = new ArrayList<>();

        for(ConcertImg concertImg : concertImgList) {
            ConcertImgDto concertImgDto = ConcertImgDto.of(concertImg);
            concertImgDtoList.add(concertImgDto);
        }
        return concertImgDtoList;
    }
}
