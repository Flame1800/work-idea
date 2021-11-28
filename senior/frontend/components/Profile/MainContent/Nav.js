import React, {useState} from "react";
import styled from "styled-components"
import OrangeSelector from "../../selectors/OrangeSelector";
import WhiteSelector from "../../selectors/WhiteSelector";

export default function Nav({chose, setChose}) {
    return (
        <Wrapper>
            {chose === 'Мои проекты' ?
                <OrangeSelector text='Мои проекты'/> :
                <WhiteSelector text="Мои проекты" onClick={ () => setChose('Мои проекты')}/>
            }
            {chose === 'Мои идеи' ?
                <OrangeSelector text='Мои идеи'/> :
                <WhiteSelector text="Мои идеи" onClick={ () => setChose('Мои идеи')}/>
            }
            {chose === 'Анкета' ?
                <OrangeSelector text='Анкета'/> :
                <WhiteSelector text="Анкета" onClick={ () => setChose('Анкета')}/>
            }
        </Wrapper>
    )
}

const Wrapper = styled.div`
  display: flex;
  width: 100%;
  border-bottom: 2px solid #000;
`;




