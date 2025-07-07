<!-- src/routes/+page.svelte -->
<div class="flex h-screen">
  <!-- 왼쪽: 코드 블록 -->
  <div class="w-1/2 bg-black">
    <!-- 윗 블럭 -->
    <div class="flex justify-between bg-white">
      <p class="py-0.25 px-2 m-1">Java 21</p>
      <button class="border-1 rounded-sm border-black py-0.25 px-2 m-1">코드 복사</button>
    </div>
    <!-- 코드 부분 -->
    <div class="bg-black text-white p-4 overflow-scroll no-scrollbar font-mono text-sm leading-tight">
      <table class="table-fixed w-full">
        <tbody>
          <!-- 반복 - data.code 줄 만큼 -->
          {#each (data.post_code ?? "").split('\n') as line, i}
            <tr>
              <!-- 코드 번호 -->
              <td class="pr-4 text-right text-gray-500 select-none w-8 border-r-1 border-neutral-500">{i + 1}</td>
              <!-- 코드 내용 -->
              <td class="align-top"><code class="language-java whitespace-pre">{line}</code></td>
            </tr>
          {/each}
        </tbody>
      </table>
    </div>
  </div>

  <!-- 오른쪽: 글 + 댓글 -->
  <div class="w-1/2 bg-neutral-200 p-3 overflow-auto">
    <div class="size-full bg-white rounded-2xl p-6">
      <!-- 게시글 부분 -->
      <div class="mb-4">
        <h2 class="text-xl font-bold">{data.post_title}</h2>
        <p class="text-sm text-gray-500">{data.post_uploader.user_name} | {data.post_createdDate}</p>
      </div>

      <div class="mb-6">
        <p class="tiptap">{@html data.post_content}</p>
      </div>
      <!-- 댓글 부분 -->
      <div class="border-t pt-4">
        <h3 class="text-lg font-semibold mb-2">💬 댓글 {data.post_comments.length}개</h3>
        <div class="space-y-4">
          <!-- 대댓글 구분은 ml-(x)에 따라서 구분 -->
          <!-- 아예 댓글 컴포넌트로 만들어서 관리 ㄱㄱ -->
          {#each data.post_comments as comments}
            <CommonComment data={comments}/>
          {/each}
        </div>
      </div>
    </div>
  </div>
</div>

<script context="module" lang="ts">
</script>

<script lang="ts">
  import "$lib/components/tiptap/tiptap.css"

  import CommonComment from '$lib/components/commonComment.svelte';
  import { testPostData } from '$lib/testPostData';

  import hljs from 'highlight.js';
  import java from 'highlight.js/lib/languages/java'
  import 'highlight.js/styles/github-dark.css'

  import { onMount } from 'svelte'

  hljs.registerLanguage('java', java)

  onMount(() => {
    const blocks = document.querySelectorAll('code')
    blocks.forEach((block) => {
      hljs.highlightElement(block as HTMLElement)
    })
  })

  const data: App.PostData = testPostData
</script>