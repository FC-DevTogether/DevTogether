<!-- 검색 및 필터 -->
<section class="flex justify-between items-center p-4 border-b">
  <form>
    <input
      type="text"
      name="serach"
      placeholder="검색어를 입력하세요"
      class="border rounded px-3 py-1 w-50 max-w-sm"
    />
  </form>
  <div>
    <select class="ml-4 border rounded px-2 py-1">
      <option>최신순</option>
      <option>조회순</option>
    </select>
    <a href="/write" class="border-1 rounded ml-1 px-2 py-1.5 hover:bg-neutral-100 active:bg-neutral-200">
      글 쓰기
    </a>
  </div>
</section>

<!-- 게시글 리스트 -->
<main class="p-4 space-y-3">
  {#if posts.length == 0}
    <div class="text-neutral-500 my-10 flex flex-col place-items-center">
      <p class="m-1">게시글이 비었습니다..</p>
      <p class="m-1">나중에 다시 방문하시거나..</p>
      <p class="m-1">새로운 코드를 공유해주세요!</p>
    </div>
  {/if}
  {#each posts as post}
    <Postpreview data={post}/>
  {/each}
  <Postpreview data={testPostData}/>
</main>

<!-- 페이지네이션 -->
<!-- 페이지 수는 어떻게 세지 -->
<!-- PageInfo 형태로 줘야하려나 -->
<footer class="flex justify-center items-center gap-2 py-4">
  {#if posts.length > 0}
    <button class="px-3 py-1 border rounded hover:bg-gray-100">이전</button>
    <button class="px-3 py-1 border rounded hover:bg-gray-100">1</button>
    <button class="px-3 py-1 border rounded hover:bg-gray-100">다음</button>
  {/if}
</footer>

<script lang="ts">
  import Postpreview from '$lib/components/postPreview.svelte'
  import { testPostData } from '$lib/testPostData';
  import { onMount } from 'svelte';

  let posts: App.PostData[] = []

  onMount ( async () => {
    try {
      // 쿼리 문 추가히기
      // page=${페이지}&limit=${리미트} 형태 문자열 추가하기
      const res = await fetch(`${import.meta.env.VITE_API_URL}/api/posts?`, {
        method: 'GET'
      })
      // 응답 체크
      if(!res.ok) throw new Error("Failed to load posts!")

      const datas = await res.json()
      posts = datas.posts
    } catch (err) {
      console.log(err)
    }
  })

</script>