import { React, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import display from "../styles/Display.module.css";
import input from "../styles/Input.module.css";

function Login() {
  // 유저 정보
  const [userInfo, setUserInfo] = useState({
    id: "",
    password: "",
  });

  const [isLoading, setIsLoading] = useState(false);
  const [showPassword, setShowPassword] = useState(false);
  const navigate = useNavigate();

  // 아이디, 비밀번호
  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setUserInfo((userInfo) => ({
      ...userInfo,
      [name]: value,
    }));
  };

  const handleLogin = async (event) => {
    if (isLoading) return; // 이미 요청 중이면 중복 요청 방지

    setIsLoading(true); // 로딩 시작

    event.preventDefault();

    if (!(userInfo.id && userInfo.password)) {
      alert("아이디와 비밀번호를 모두 입력해주세요.");
      setIsLoading(false);
      return;
    }

    const { id, password } = userInfo;

    try {
      const response = await axios.post("http://localhost:8080/auth/login", {
        id,
        password,
      });
      if (response.status === 200) {
        const token = response.data; // JWT 토큰
        localStorage.setItem("token", token); // 토큰 저장
        alert("로그인되었습니다.");
        navigate("/home"); // 로그인 후 이동할 페이지
      }
    } catch (error) {
      console.error("로그인 실패:", error.response?.data || error.message);
      alert("아이디 또는 비밀번호가 틀렸습니다.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div class={display.xyCenter}>
      <h1 class={`${display.titleFont}`} align="center">
        로그인
      </h1>
      <div>
        <form
          class={`${display.nameFont}`}
          onChange={handleInputChange}
          onSubmit={handleLogin}
        >
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
              type={showPassword ? "text" : "password"}
              maxLength={20}
              value={userInfo.password}
              name="password"
            />
            <button
              type="button"
              onClick={(event) => {
                event.preventDefault();
                setShowPassword(!showPassword);
              }} // 버튼 클릭 시 상태 변경
              style={{
                position: "absolute",
                right: "10px",
                top: "50%",
                transform: "translateY(-50%)",
                background: "none",
                border: "none",
                cursor: "pointer",
              }}
            >
              {showPassword ? "👁️" : "🙈"} {/* 아이콘 변경 */}
            </button>
          </div>
          <div align="center">
            <button type="submit">로그인</button>
          </div>
        </form>
      </div>
      <div>
        <p>계정이 없으신가요?</p>
        <a href="/signup">회원가입</a>
      </div>
    </div>
  );
}

export default Login;
