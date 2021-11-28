import styled from "styled-components";
import React from "react";


export default function WhiteButton({text}) {
    return (
        <Wrapper>{text}</Wrapper>
    );
}

const Wrapper = styled.button`
    display: flex;
    background-color: white;
    color: #18191F;
  border: 2px solid #18191F;
    padding: 14px 20px;
  min-width: 214px;
  height: 60px;
  font-size: 21px;
    border-radius: 12px;
    text-align: center;
    font-weight: bold;
    text-decoration: none;
    align-self: center;
    cursor: pointer;
  box-shadow: 0px 6px 0px #18191F;

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