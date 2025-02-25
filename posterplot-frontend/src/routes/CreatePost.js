import { React, useState, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

import display from "../styles/Display.module.css";
import input from "../styles/Input.module.css";

function CreatePost() {
  const [userText, setUserText] = useState("");
  const [AIText, setAIText] = useState("");

  const textareaRef = useRef(null);

  const handleInputChange = (event) => {
    setUserText(event.target.value);
    adjustHeight();
  };

  const adjustHeight = () => {
    if (textareaRef.current) {
      textareaRef.current.style.height = "250px"; // 최소 높이 250px
      textareaRef.current.style.height =
        textareaRef.current.scrollHeight + "px";
    }
  };

  return (
    <div className={display.container}>
      <h1>스토리 작성하기</h1>
      <div>
        <div>
          <form>
            <input
              className={input.postingTitleInput}
              placeholder="영화제목..."
            />
            <div>
              <label>장르선택</label>
              <select name="genres">
                <option value="action">액션</option>
                <option value="crime">범죄</option>
                <option value="romance">로맨스</option>
                <option value="sci_fi">SF</option>
                <option value="comedy">코미디</option>
                <option value="sports">스포츠</option>
                <option value="fantasy">판타지</option>
                <option value="music">음악</option>
                <option value="musical">뮤지컬</option>
                <option value="war">전쟁</option>
                <option value="horror">호러</option>
                <option value="thriller">스릴러</option>
              </select>
            </div>
            <textarea
              ref={textareaRef}
              className={input.textarea}
              value={userText}
              onChange={handleInputChange}
              placeholder="사용자가 작성"
            />
            <textarea className={input.textarea} placeholder="AI STORY" />
          </form>
        </div>
      </div>
    </div>
  );
}

export default CreatePost;
