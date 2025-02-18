import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";

import display from "../styles/Display.module.css";
import input from "../styles/Input.module.css";

function Login() {
  // 유저 정보
  const [userInfo, setUserInfo] = useState({
    id: "",
    password: "",
  });

  // 아이디, 비밀번호
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUserInfo((userInfo) => ({
      ...userInfo,
      [name]: value,
    }));
  };
  return (
    <div class={display.xyCenter}>
      <h1 class={`${display.titleFont}`} align="center">
        로그인
      </h1>
      <div>
        <form class={`${display.nameFont}`} onChange={handleInputChange}>
          <div style={{ margin: "10px" }}>
            <label class={`${input.greetingLabel}`}>아이디</label>
            <input
              class={input.greetingInput}
              type="text"
              maxLength={20}
              value={userInfo.id}
              name="id"
            />
          </div>
          <div style={{ margin: "10px" }}>
            <label class={`${input.greetingLabel}`}>비밀번호</label>
            <input
              class={input.greetingInput}
              type="password"
              maxLength={20}
              value={userInfo.password}
              name="password"
            />
          </div>
        </form>
        <div align="center">
          <button>로그인</button>
        </div>
      </div>
    </div>
  );
}

export default Login;
