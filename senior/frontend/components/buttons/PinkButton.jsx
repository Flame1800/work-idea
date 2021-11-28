import styled from "styled-components";
import React from "react";


export default function PinkButton({text}) {
    return (
        <Wrapper>{text}</Wrapper>
    );
}

const Wrapper = styled.button`
    display: flex;
    background-color: #FF89BB;
  color: #18191F;
  border: 2px solid #18191F;
  padding: 10px 16px;
  height: auto;
  width: auto;
  border-radius: 12px;
  text-align: center;
  font-weight: 800;
  font-style: normal;
  text-decoration: none;
  align-self: center;
  box-shadow: 0px 6px 0px #18191F;
  cursor: pointer;
  margin-left: 20px;

  &:active {
    animation: btnClick;
    animation-duration: 0.4s;
  }

  @keyframes btnClick {
    0% {
      box-shadow: 0px 6px 0px #18191F;
      margin-top: 0px;
    }
    50% {
      box-shadow: 0px 0px 0px #18191F;
      margin-top: 6px;
    }
    100% {
      box-shadow: 0px 6px 0px #18191F;
      margin-top: 0px;
    }
  }
`;