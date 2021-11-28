import styled from "styled-components";
import React from "react";
import PinkButton from "./buttons/PinkButton";
import {useAppSelector} from "../redux/hooks";
import {API} from "../libs/api";
import {useRouter} from "next/router";


export default function Like({ idea }) {

    return (
        <div>
            <PinkButton text={"Мне нравится"} />
        </div>
    );
}

