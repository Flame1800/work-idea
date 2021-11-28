export const sortUsers = (data, categories, skills) => {
    console.log(data, skills)
    if (!(data?.constructor.name === 'Array')) return 'not array';
    if (skills) {
        data = data.filter(element => element?.categories_of_specializations.map(spec => spec.name)?.includes(skills));
        if (categories === null) return data;
    }
    return data.filter(element => element === categories)
};

export const sortCards = (data, title, text) => {
    if (!(data.constructor.name === 'Array')) return 'not array';
    if (text) {
        data = data.filter(element => element?.category?.name === text.label);
        if (title === null) return data;
    }
    return data.filter(element => element === title)
};