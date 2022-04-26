package org.deco.gachicoding.presentation.question;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.deco.gachicoding.api.question.dto.QuestionResponseDto;
import org.deco.gachicoding.api.question.dto.QuestionSaveRequestDto;
import org.deco.gachicoding.api.question.dto.QuestionUpdateRequestDto;
import org.deco.gachicoding.api.question.service.QuestionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RestQuestionController {
    private final QuestionService questionService;

    @ApiOperation(value = "질문 리스트")
    @GetMapping("/question/list/{page}")
    public Page<QuestionResponseDto> getQuestionListByKeyword(@RequestParam(value = "keyword", defaultValue = "") String keyword, @PathVariable int page){
        return questionService.getQuestionListByKeyword(keyword, page);
    }

    @ApiOperation(value = "질문 디테일")
    @GetMapping("/question/detail/{questionIdx}")
    public QuestionResponseDto getQuestionDetailById(@PathVariable Long questionIdx){
        return questionService.getQuestionDetailById(questionIdx);
    }

    @ApiOperation(value = "질문 등록")
    @PostMapping("/question")
    public Long registerQuestion(@Valid @RequestBody QuestionSaveRequestDto dto){
        return questionService.registerQuestion(dto);
    }

    @ApiOperation(value = "질문 수정")
    @PutMapping("/question/modify/{questionIdx}")
    public QuestionResponseDto modifyQuestionById(@PathVariable Long questionIdx, @RequestBody QuestionUpdateRequestDto dto){
        return questionService.modifyQuestionById(questionIdx, dto);
    }

    @ApiOperation(value = "질문 비활성화")
    @DeleteMapping("/question/{questionIdx}")
    public Long removeQuestion(@PathVariable Long questionIdx){
        return questionService.removeQuestion(questionIdx);
    }
}