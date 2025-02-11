import React from "react";
import { useNavigate } from "react-router-dom";

function Home() {
  const navigate = useNavigate();

  // 로그인 버튼 클릭 시 실행되는 함수
  const handleLoginClick = () => {
    navigate("/Login"); // /login 경로로 이동
  };

  // 회원가입 버튼 클릭 시 실행되는 함수
  const handleSignUpClick = () => {
    navigate("/Signup"); // /signup 경로로 이동
  };

  return (
    <div>
      Home
      <button onClick={handleLoginClick}>로그인하기</button>
      <button onClick={handleSignUpClick}>회원가입하기</button>
    </div>
  );
}

export default Home;
