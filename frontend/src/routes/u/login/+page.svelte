<div class="bg-white w-full h-100 rounded-2xl mb-1">
  <div class="h-full flex justify-center items-center text-2xl font-bold">
    로그인 페이지입니다.
  </div>
</div>
<form on:submit|preventDefault={handleSubmit} class="w-full h-1/3">
  <input name="username" type="text" placeholder="아이디를 입력하세요...">
  <input name="password" type="password" placeholder="비밀번호를 입력하세요...">
  <div class="flex justify-between">
    <button type="submit" class="bg-sky-400 text-white mr-2">로그인</button>
    <button type="button" on:click={() => {goto("/u/signup/")}} class="bg-white border-1">회원가입</button>
  </div>
</form>

<script lang='ts'>
    import { goto } from "$app/navigation";

  const handleSubmit = async (event: SubmitEvent) => {
    const form = event.target as HTMLFormElement
    const data = new FormData(form)

    const reqData: Api.PostLoginRequest = {
      username: data.get('username') + '',
      password: data.get('password') + ''
    }

    // TODO: null 체크하는 함수 만들어서 빼두기
    if (reqData.username == 'null' || reqData.password == 'null'
      || reqData.username == '' || reqData.password == ''
    ) {
      console.log(reqData)
      alert("비어있는 필드가 존재합니다!")
      return
    }

    console.log(reqData)

    const jsonData = JSON.stringify( reqData )

    try {
      const res = await fetch(`${import.meta.env.VITE_API_URL}/api/user/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: jsonData
      })

      console.log(res)
      
      if(res.status == 403) {
        alert('아이디 또는 비밀번호가 틀렸습니다!')
        return
      }
      // 대답이 어떻게 나오려나
      // 로그인 성공했어용 -> user 상태 변경하고 어디론가 보내기
        // 어디로 보내지? /? 아니면 이전 페이지?
      // 실패했어용 -> "아이디 또는 비밀번호가 틀렸습니다."
      // goto("/")
    } catch(err) {
      alert('로그인에 실패했습니다!')
      console.log(err)
    }
  }
</script>