<script>
  import "./tiptap.css"

  import Placeholder from '@tiptap/extension-placeholder'
  import Highlight from '@tiptap/extension-highlight'
  import Typography from '@tiptap/extension-typography'
  import StarterKit from "@tiptap/starter-kit"
  import { Editor } from "@tiptap/core"
  import { onMount } from "svelte"

  let element
  let editor
  const placeholder = `내용을 입력하세요...
markdown 문법 또한
부분적으로 사용 가능합니다...`
  export let className = ""
  export let content = ""

  onMount(() => {
    editor = new Editor({
      element: element,
      extensions: [
        StarterKit,
        Highlight,
        Typography,
        Placeholder.configure({
          placeholder: placeholder,          
        }),
      ],
      editorProps: {
        attributes: {
          class: `tiptap ${className}`
        }
      }
      ,
      content,
      onUpdate({ editor }) {
        content = editor.getHTML();
      }
    })
  })
</script>

<div bind:this={element}></div>