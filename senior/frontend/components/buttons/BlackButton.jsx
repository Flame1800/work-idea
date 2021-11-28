import styled from "styled-components";
import React from "react";


export default function BlackButton({ text }) {
    return (
        <Align>{text}</Align>
    );
}

const Align = styled.button`
    display: flex;
    background-color: #18191F;
    color: white;
    border: 2px solid #18191F;
    padding: 10px 16px;
    height: auto;
    width: auto;
    border-radius: 12px;
    text-align: center;
    font-weight: bold;
    text-decoration: none;
    align-self: center;
    cursor: pointer;
`;