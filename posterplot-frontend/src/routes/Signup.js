import React from "react";
import { useState, useEffect } from "react";

function Signup() {
  // 유저 정보
  const [userInfo, setUserInfo] = useState({
    id: "",
    email: "",
    password: "",
    passwordConfirm: "",
  });

  const [error, setError] = useState("");

  // 아이디, 이메일, 비밀번호, 비밀번호 확인
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUserInfo((userInfo) => ({
      ...userInfo,
      [name]: value,
    }));
  };
  // 6자 이상 20자 이하, 알파벳 대소문자와 숫자만 포함하는 경우 true 리턴
  const idValid = /^[a-zA-Z0-9]{6,20}$/.test(userInfo.id);

  // 이메일 형태를 가진 경우 true 리턴
  const emailValid = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(
    userInfo.email
  );

  // 8자 이상 20자 이하, 하나 이상의 숫자, 하나 이상의 알파벳을 포함하는 경우 true 리턴
  const passwordValid = /^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$/.test(
    userInfo.password
  );

  // isVaild 변수 (boolean)
  const isVaild =
    idValid &&
    emailValid &&
    passwordValid &&
    userInfo.password === userInfo.passwordConfirm;

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("회원가입 제출");
    // 여기에서 회원가입 API 호출 로직을 작성
  };

  return (
    <div>
      <h1>회원가입</h1>
      <div>
        <form onChange={handleInputChange} onSubmit={handleSubmit}>
          <div>
            <label>아이디</label>
            <input
              type="text"
              placeholder="6자 이상의 영문 혹은 영문과 숫자를 조합"
              maxLength={20}
              value={userInfo.id}
              name="id"
            />
            <p>**6자 이상 20자 이하</p>
            <p>**알파벳 및 숫자만 포함</p>
            <button disabled={!idValid}>중복확인</button>
          </div>
          <div>
            <label>이메일</label>
            <p>
              아이디 및 패스워드 분실 시 이메일을 통해 본인인증을 진행합니다
            </p>
            <input
              type="email"
              placeholder="예: posterplot@plot.com"
              value={userInfo.email}
              name="email"
            />
            <button disabled={!emailValid}>인증번호 받기</button>
          </div>
          <div>
            <input type="string" placeholder="인증번호를 입력해주세요" />
          </div>
          <div>
            <label>비밀번호</label>
            <input
              type="password"
              placeholder="비밀번호를 입력해주세요"
              maxLength={20}
              value={userInfo.password}
              name="password"
            />
            {passwordValid && <p>유효한 패스워드</p>}
          </div>
          <div>
            <label>비밀번호 확인</label>
            <input
              type="password"
              placeholder="비밀번호를 한번 더 입력해주세요"
              maxLength={20}
              value={userInfo.passwordConfirm}
              name="passwordConfirm"
            />
            {userInfo.password !== userInfo.passwordConfirm && (
              <p>비밀번호가 일치하지 않습니다</p>
            )}
          </div>
        </form>
        <button disabled={!isVaild} type="submit">
          가입하기
        </button>
      </div>
    </div>
  );
}

export default Signup;
