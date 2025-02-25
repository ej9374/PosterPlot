import logo from "./logo.svg";
import "./App.css";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import Home from "./routes/Home";
import Signup from "./routes/Signup";
import Login from "./routes/Login";
import CreatePost from "./routes/CreatePost";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        {/** 홈 화면 */}
        <Route path="/signup" element={<Signup />} />
        {/** 회원가입 화면 */}
        <Route path="/login" element={<Login />} />
        {/** 로그인 화면 */}
        <Route path="/createpost" element={<CreatePost />} />
      </Routes>
    </Router>
  );
}

export default App;
