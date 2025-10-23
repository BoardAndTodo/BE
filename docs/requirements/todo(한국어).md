---
title: Todo 테스트 시나리오
---

# Todo Test Scenarios

## 1. 기능 개요
- Todo 등록 / 조회 / 수정 / 삭제 기능 검증
- Controller 통합 테스트 + Service 단위 테스트 기반
- Repository는 통합 테스트로 커버

---

## 2. 테스트 시나리오 (TDD Checklist)

### Controller & Repository 통합 테스트
- [x] post_Todo등록_성공하면_200반환()
- [x] post_Todo등록_content없으면_400반환()
- [x] get_Todo조회_성공하면_200반환()
- [x] get_Todo조회_ID없으면_404반환()
- [x] get_Todo전체조회_성공하면_200반환()
- [x] put_Todo수정_성공하면_200반환()
- [x] put_Todo수정_ID없으면_404반환()
- [x] put_Todo상태변경_성공하면_200반환()
- [x] delete_Todo삭제_성공하면_200반환()
- [x] delete_Todo삭제_ID없으면_404반환()
---

### Service 단위 테스트
- [x] findById_성공시_TodoResponseDto반환()
- [x] findById_ID없으면_예외발생()
- [x] updateStatus_ID없으면_예외발생()
- [x] deleteById_ID없으면_예외발생()
- [x] repository_정상호출_검증()
---
