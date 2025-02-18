import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";

import display from "../styles/Display.module.css";
import input from "../styles/Input.module.css";

function Signup() {
  // 유저 정보
  const [userInfo, setUserInfo] = useState({
    id: "",
    email: "",
    code: "",
    password: "",
    passwordConfirm: "",
  });

  const [isIdDuplicated, setIsIdDuplicated] = useState(null);
  const [error, setError] = useState("");

  // 6자 이상 20자 이하, 알파벳 대소문자와 숫자만 포함하는 경우 true 리턴
  const idValid = /^(?=.*[a-zA-Z])[a-zA-Z0-9]{6,20}$/.test(userInfo.id);

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
    idValid && // 올바른 형태의 아이디인가?
    isIdDuplicated === false && // 아이디가 중복되었는가?
    emailValid && // 올바른 형태의 이메일인가?
    passwordValid && // 올바른 형태의 비밀번호인가?
    userInfo.password === userInfo.passwordConfirm; // 비밀번호와 비밀번호 확인에 입력한 내용이 일치하는가?

  // 유저 정보 입력
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUserInfo((userInfo) => ({
      ...userInfo,
      [name]: value,
    }));
  };

  // 아이디 중복 체크
  const checkIdAvailability = async (id) => {
    try {
      const response = await axios.get(`http://localhost:8080/auth/checkId`, {
        params: { id: id },
      });
      if (response.status === 200) {
        setIsIdDuplicated(false); // 중복되지 않는 아이디
      } else if (response.status === 409) {
        setIsIdDuplicated(true); // 중복된 아이디
      } else {
        setIsIdDuplicated(null);
      }
    } catch (error) {
      console.error("Error checking ID:", error);
      setIsIdDuplicated(null);
    }
  };

  const sendVerificationEmail = async (email) => {
    try {
      console.log(email);
      const response = await axios.post(
        "http://localhost:8080/auth/mailSend",
        null,
        {
          params: { email: email },
        }
      );
      if (response.status === 200) {
        alert("인증번호가 발송되었습니다."); // 이메일 전송 완료
      } else if (response.status === 409) {
        alert("이미 사용중인 이메일입니다."); // 중복된 이메일
      } else {
        console.log("오류");
      }
    } catch (error) {
      console.error("Error sending email:", error);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    console.log("회원가입 제출");
    // 서버에서 전송한 인증번호와 사용자가 입력한 인증번호가 일치하는가?
    // 여기에서 회원가입 API 호출 로직을 작성
  };

  return (
    <div class={display.xyCenter}>
      <h1 class={`${display.titleFont}`} align="center">
        회원가입
      </h1>
      <div>
        <form
          class={`${display.nameFont}`}
          onChange={handleInputChange}
          onSubmit={handleSubmit}
        >
          <div style={{ margin: "10px" }}>
            <label class={`${input.greetingLabel}`}>아이디</label>
            <input
              class={input.greetingInput}
              type="text"
              placeholder="6자 이상의 영문 혹은 영문과 숫자를 조합"
              maxLength={20}
              value={userInfo.id}
              name="id"
            />
            <button
              onClick={() => checkIdAvailability(userInfo.id)}
              disabled={!idValid}
            >
              중복확인
            </button>
            {isIdDuplicated === null && <p>아이디를 입력해주세요.</p>}
            {isIdDuplicated === false && <p>사용 가능한 아이디입니다.</p>}
            {isIdDuplicated === true && <p>이미 사용 중인 아이디입니다.</p>}
          </div>
          <div style={{ margin: "10px" }}>
            <label class={`${input.greetingLabel}`}>이메일</label>
            <input
              class={input.greetingInput}
              type="email"
              placeholder="예: posterplot@plot.com"
              value={userInfo.email}
              name="email"
            />
            <button
              disabled={!emailValid}
              onClick={() => sendVerificationEmail(userInfo.email)}
            >
              인증번호 받기
            </button>
          </div>
          <div style={{ margin: "10px" }}>
            <input
              class={input.greetingInput}
              style={{ marginLeft: "150px" }}
              type="string"
              placeholder="인증번호를 입력해주세요"
              value={userInfo.code}
              name="code"
            />
          </div>
          <div style={{ margin: "10px" }}>
            <label class={`${input.greetingLabel}`}>비밀번호</label>
            <input
              class={input.greetingInput}
              type="password"
              placeholder="비밀번호를 입력해주세요"
              maxLength={20}
              value={userInfo.password}
              name="password"
            />
            {passwordValid && <p>사용 가능한 비밀번호입니다.</p>}
          </div>
          <div style={{ margin: "10px" }}>
            <label class={`${input.greetingLabel}`}>비밀번호 확인</label>
            <input
              class={input.greetingInput}
              type="password"
              placeholder="비밀번호를 한번 더 입력해주세요"
              maxLength={20}
              value={userInfo.passwordConfirm}
              name="passwordConfirm"
            />
            <div style={{ marginLeft: "150px" }}>
              {userInfo.passwordConfirm &&
                (userInfo.password === userInfo.passwordConfirm ? (
                  <p>비밀번호가 일치합니다</p>
                ) : (
                  <p>비밀번호가 일치하지 않습니다</p>
                ))}
            </div>
          </div>
        </form>
        <div align="center">
          <button disabled={!isVaild} type="submit">
            가입하기
          </button>
        </div>
      </div>
    </div>
  );
}

export default Signup;
