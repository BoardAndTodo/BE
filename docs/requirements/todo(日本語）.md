---
title: Todo テストシナリオ（しなりお）
---

# Todo Test Scenarios

## 1. 機能概要（きのう がいよう）
- Todo登録（とうろく） / 取得（しゅとく） / 更新（こうしん） / 削除（さくじょ） 機能（きのう）の検証（けんしょう）
- Controller統合（とうごう）テスト + Service単体（たんたい）テスト構成（こうせい）
- Repositoryは統合テストでカバー

---

## 2. テストシナリオ（しなりお） (TDD Checklist)

### Controller & Repository 統合（とうごう）テスト
- [x] post_Todo作成（さくせい）_成功（せいこう）時_200を返（かえ）す()
- [x] post_Todo作成_内容（ないよう）が空（から）の場合（ばあい）_400を返す()
- [x] get_Todo取得（しゅとく）_成功時_200を返す()
- [x] get_Todo取得_存在（そんざい）しないIDの場合_404を返す()
- [x] get_Todo一覧（いちらん）取得_成功時_200を返す()
- [x] put_Todo内容更新（こうしん）_成功時_200を返す()
- [x] put_Todo内容更新_存在しないIDなら_404を返す()
- [x] put_Todo状態（じょうたい）更新_成功時_200を返す()
- [x] delete_Todo削除（さくじょ）_成功時_200を返す()
- [x] delete_Todo削除_存在しないIDなら_404を返す()
---

### Service 単体（たんたい）テスト
- [x] findById_成功時_TodoResponseDtoを返す()
- [x] findById_IDが存在しない場合（ばあい）_例外（れいがい）発生（はっせい）()
- [x] updateStatus_IDが存在しない場合_例外発生()
- [x] deleteById_IDが存在しない場合_例外発生()
---
