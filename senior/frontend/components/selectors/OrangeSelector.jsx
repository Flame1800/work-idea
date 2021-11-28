import styled from "styled-components";
import React from "react";


export default function OrangeSelector({text}) {
    return (
        <Wrapper>{text}</Wrapper>
    );
}

const Wrapper = styled.button`
display: flex;
background-color: #FFBD12;
color: #18191F;
border: 2px solid #18191F;
border-radius: 16px 16px 0px 0px;
padding: 16px 32px;
height: auto;
width: auto;
text-align: center;
font-weight: bold;
text-decoration: none;
align-self: center;
  font-size: 27px;
  font-weight: 800;
  margin-right: 10px;
`;