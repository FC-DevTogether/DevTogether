<script lang='ts'>
  export let data: App.PostData

  const stripHtmlFast = (html: string) => {
    return html.replace(/<[^>]*>/g, '')
  }

  const summary = stripHtmlFast(data.post_content.slice(0,128)) + "..."
</script>

<a href={`/board/`+data.post_id} class="m-1">
  <div class="border rounded p-3 hover:bg-gray-50 ">
    <h2 class="text-2xl font-semibold">{data.post_title}</h2>
    <div class="flex justify-between">
      <p class="text-sm text-gray-400">{data.post_uploader.user_name} | {data.post_createdDate}</p>
      <p class="text-gray-400"> {data.post_id}</p>
    </div>
    <p class="border-t-1 border-neutral-600 text-neutral-500 ">{stripHtmlFast(summary)}</p>
  </div>
</a>