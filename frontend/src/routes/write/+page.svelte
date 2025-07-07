<!-- src/routes/+page.svelte -->
<form on:submit|preventDefault={handleSubmit} class="flex h-screen">
  <!-- 왼쪽: 코드 블록 -->
  <div class="w-1/2 bg-black">
    <!-- 윗 블럭 -->
    <div class="flex justify-between bg-white">
      <p class="py-0.25 px-2 m-1">Java 21</p>
      <button class="border-1 rounded-sm border-black py-0.25 px-2 m-1">코드 복사</button>
    </div>
    <!-- 코드 부분 -->
    <div class="p-2">
      <textarea
        class="text-white border-white border rounded-lg w-full h-200 p-2"
        name="code"
        placeholder="코드를 입력하세요."
      ></textarea>
    </div>
  </div>

  <!-- 오른쪽: 글 + 댓글 -->
  <div class="w-1/2 bg-neutral-200 p-3 overflow-auto">
    <div class="size-full bg-white rounded-2xl p-6">
      <!-- 게시글 부분 -->
      <div class="mb-4">
        <input
          class="font-bold text-2xl w-full border rounded-lg p-2"
          name="title"
          placeholder="제목을 입력하세요."
        />
      </div>

      <div class="">
        <!--구 내용 입력 (textarea)-->
        <!--<textarea
          class="w-full border rounded-xl p-2 h-100"
          name="content"
          placeholder="내용을 입력하세요."
        ></textarea>
        -->
        <Tiptap bind:content className=" w-full h-150 border-1 border-black p-2 rounded-lg mb-2 overflow-y-scroll"/>
        <input type="hidden" name="content" value={content} />
      </div>

      <div class="flex justify-end">
        <button
          type="submit"
          class="bg-sky-400 hover:bg-sky-500 hover:scale-[102%] active:bg-sky-600 active:scale-[98%] py-1 px-4 rounded-xl text-neutral-50"
        >작성하기</button>
      </div>
    </div>
  </div>
</form>

<script context="module" lang="ts">
</script>

<script lang="ts">
  import { goto } from "$app/navigation";
  import Tiptap from "$lib/components/tiptap/tiptap.svelte";
  //
  let content = ''

  // 폼 제출
  const handleSubmit = async (event: SubmitEvent) => {

    const form = event.target as HTMLFormElement
    const data = new FormData(form)

    // 데이터 정제
    const title = data.get('title')
    const code = data.get('code')

    // 데이터 묶어서 Json 화
    const jsonData = JSON.stringify({ title, content, code })

    console.log(jsonData)

    try {
      // 주소로 POST 요청 보내기
      const res = await fetch('http://localhost:8080/posts', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: jsonData
      })

      // 요청 유효성 확인
      if (!res.ok) {
        throw Error("Fetch Failed!")
      } 
      else {
        const resId = await res.json().then(data => data.id)
        goto(`/board/`+{resId})
      }
    } catch (err) {
      alert("작성 실패했습니다!")
      console.log(err)
    }
  }
</script>