import styled from "styled-components";
import React from "react";


export default function OrangeButton({text}) {
    return (
        <Wrapper>{text}</Wrapper>
    );
}

const Wrapper = styled.button`
    display: flex;
    background-color: #F95A2C;
    color: white;
    padding: 16px;
    height: auto;
    width: auto;
    border-radius: 12px;
    text-align: center;
    text-decoration: none;
    align-self: center;
    box-shadow: 0px 6px 0px #18191F;
  font-size: 21px;
  font-weight: 800;
  cursor: pointer;

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