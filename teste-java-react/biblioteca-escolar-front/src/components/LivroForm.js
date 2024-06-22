import React, { useState } from 'react';
import axios from 'axios';

const LivroForm = () => {
    const [titulo, setTitulo] = useState('');
    const [autor, setAutor] = useState('');
    const [anoPublicacao, setAnoPublicacao] = useState('');
    const [genero, setGenero] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        const novoLivro = { titulo, autor, anoPublicacao, genero };
        axios.post('http://localhost:8080/api/livros', novoLivro)
            .then(response => console.log(response.data))
            .catch(error => console.error(error));
    };

    return (
        <form onSubmit={handleSubmit}>
            <div>
                <label>Título</label>
                <input
                    type="text"
                    value={titulo}
                    onChange={(e) => setTitulo(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Autor</label>
                <input
                    type="text"
                    value={autor}
                    onChange={(e) => setAutor(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Ano de Publicação</label>
                <input
                    type="number"
                    value={anoPublicacao}
                    onChange={(e) => setAnoPublicacao(e.target.value)}
                    required
                />
            </div>
            <div>
                <label>Gênero</label>
                <input
                    type="text"
                    value={genero}
                    onChange={(e) => setGenero(e.target.value)}
                    required
                />
            </div>
            <button type="submit">Cadastrar Livro</button>
        </form>
    );
};

export default LivroForm;
