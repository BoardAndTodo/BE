# Board Test Scenarios

## 1. 기능 개요
- 게시글 등록 / 조회 / 수정 / 삭제 기능 검증
- Controller 통합 테스트 + Service 단위 테스트 기반
- Repository는 통합 테스트로 커버

---

## 2. 테스트 시나리오 (TDD Checklist)

### Controller & Repository 통합 테스트
- [x] post_게시글등록_성공하면200을반환()
- [x] post_게시글등록시_제목이없으면_400을반환()
- [x] post_게시글등록시_내용이없으면_400을반환()
- [x] get_게시글조회_성공하면200을반환()
- [x] put_게시글수정_성공하면200을반환()
- [x] put_게시글수정_제목이나내용이없으면_400을반환()
- [x] put_게시글수정_존재하지않는게시글이면_404를반환()
- [x] delete_게시글삭제_성공하면200을반환()
- [x] delete_게시글삭제_존재하지않는게시글이면_404를반환()
- 
---

### Service 단위 테스트
- [x] findById_성공_시_BoardResponseDto_반환()
- [x] findById_아이디없을때_예외발생()
- [x] deleteById_아이디없을때_예외발생()
- [x] updateById_상태이상시_예외발생()
- [x] repository_정상호출_검증()
---



