---
title: StopWatch 테스트 시나리오
---

# StudyTime Test Scenarios

## 1. 기능 개요
- 공부 시간(시작~종료까지 총 초 단위)을 기록하고 DB에 저장하는 기능 검증
- Controller 통합 테스트 + Service 단위 테스트 기반
- Repository는 통합 테스트로 커버


---

## 2. 테스트 시나리오 (TDD Checklist)

### Controller & Repository 통합 테스트
// Save 관련 
- [x] post_공부시간저장_성공하면_200반환()
- [x] post_공부시간저장_time없으면_400반환()


//findOne 관련
- [] get_공부시간조회_성공하면_200반환()
- [] get_공부시간조회_값없으면_0반환_응답200반환()

//findAll 관련
- [x] pageable_페이징조회_성공()
- [] get_공부시간전체조회_성공하면_200반환()
---

### Service 단위 테스트
// Save 관련
- [x] save_정상입력_저장성공()
- [x] save_시간값없으면_예외발생()
- [x] repository_save_정상호출_검증()

//findOne 관련
- [x] findOne_저장된값있으면_조회성공()
- [x] findOne_저장된값없으면_시간_0_반환()
- 

//findAll 관련
- [x] findAll_저장된값있으면_조회성공()
- [x] findAll_저장된값없으면_빈리스트반환()
- [x] repository_findAll_정상호출_검증()

---

