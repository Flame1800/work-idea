import React from "react";
import styled from "styled-components"
import OrangeCircle from "../../figures/OrangeCircle";
import Link from "next/link";
import WhiteButtonBig from "../../buttons/WhiteButtonBig";

export default function LinkCont() {
    return (
        <div>
            <Wrapper>
                <Link href='/projects/new' >
                    <a>
                        <Item>
                            <Dot>
                                <OrangeCircle />
                                <Text>Создать проект</Text>
                            </Dot>
                            <img src="/images/vector.png" height={18} width={12} />
                        </Item>
                    </a>
                </Link>
                <Link href='/ideas/new' >
                    <a>
                        <Item>
                            <Dot>
                                <OrangeCircle />
                                <Text>Предложить идею</Text>
                            </Dot>
                            <img src="/images/vector.png" height={18} width={12} />
                        </Item>
                    </a>
                </Link>
            </Wrapper>
        </div>
)
}


const Wrapper = styled.div`
  width: 320px;
  height: 104px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  border: 1px solid #000;
  border-bottom: 5px solid #000;
  padding: 30px;
  border-radius: 16px;
  margin-bottom: 30px;
`;

const Text = styled.div`
  font-size: 17px;
  margin-left: 10px;
  font-weight: 800;
`

const Item = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
  
  &:hover {
      div {
        color: #F95A2C;
      }
  }
`




const Dot = styled.div`
  display: flex;
  align-items: center;
`

