<div class="flex h-screen">
  <!-- 왼쪽: 코드 블록 -->
  <div class="w-1/2 bg-onedark-black">
    <!-- 윗 블럭 -->
    <div class="flex justify-between bg-white">
      <p class="py-0.25 px-2 m-1">Java 21</p>
      <button class="border-1 rounded-sm border-black py-0.25 px-2 m-1">코드 복사</button>
    </div>
    <!-- 코드 부분 -->
    <div class="overflow-scroll no-scrollbar font-mono text-sm leading-tight">
      <!--구 코드 구현 (table 방식)
      <table class="table-fixed w-full">
        <tbody>
          {#each (data.post_code ?? "").split('\n') as line, i}
            <tr>
              <td class="pr-4 text-right text-gray-500 select-none w-8 border-r-1 border-neutral-500">{i + 1}</td>
              <td class="align-top"><code class="language-java whitespace-pre">{line}</code></td>
            </tr>
          {/each}
        </tbody>
      </table>-->
      <Codemirror code={post.code} readOnly={true} className="text-lg"/>
    </div>
  </div>

  <!-- 오른쪽: 글 + 댓글 -->
  <div class="w-1/2 bg-neutral-200 p-3 overflow-auto h-fit">
    <div class="size-full bg-white rounded-2xl p-6">
      <!-- 게시글 부분 -->
      <div class="mb-4">
        <h2 class="text-xl font-bold">{post.title}</h2>
        <p class="text-sm text-gray-500">{post.author.username} | {post.createDate}</p>
      </div>

      <div class="mb-6">
        <p class="tiptap">{@html post.content}</p>
      </div>
      <!-- 댓글 부분 -->
      <div class="border-t pt-4">
        <h3 class="text-lg font-semibold mb-2">💬 댓글 {post.comments.length}개</h3>
        <div class="space-y-4">
          <CommentRenderer comments={post.comments} />
          <form on:submit|preventDefault={handleSubmit}>
            <div class="w-full py-2">
              <textarea
                class="w-full h-16 bg-white rounded-xl p-2 border"
                placeholder="댓글을 입력하세요.."
              ></textarea>
              <div class="flex justify-end">
                <button class="px-6 py-1 text-white bg-sky-300 rounded-xl hover:bg-sky-400 hover:scale-[105%] active:bg-sky-500 active:scale-[97%]">작성</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>

<script context="module" lang="ts">
</script>

<script lang="ts">
  import CommentRenderer from '$lib/components/CommentRenderer.svelte';
  import Codemirror from '$lib/components/codemirror/codemirror.svelte';

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

  export let data: Api.GetPostsByPostId
  const post = data.post

  console.log(post)

  const handleSubmit = () => {

  }
</script>