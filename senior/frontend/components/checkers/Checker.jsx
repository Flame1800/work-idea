import React, {useState} from "react";
import styled from "styled-components"
import Image from "next/image";


export default function Checker({img}) {
    const [flag, setFlag] = useState(true);

    const changeFlag = () => {
        setFlag(!flag);
    };

    return (
        <Wrapper onClick={changeFlag}>
            <Cont  >
                {flag ?
                    <img
                        src={img || '/images/check.png'}
                        width={17}
                        height={17}
                        alt='Нет картинки'
                    />
                    :
                    null}
            </Cont>
            <Text>Готов к предложениям</Text>
        </Wrapper>
    )
}

const Wrapper = styled.div`

`

const Text = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  margin-top: -25px;
    margin-left: 50px;
    font-size: 16px;
    font-weight: 800;
`

const Cont = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 32px;
  height: 32px;
  background: #FFBD12;
  border: 2px solid #18191F;
  border-radius: 8px;
`;