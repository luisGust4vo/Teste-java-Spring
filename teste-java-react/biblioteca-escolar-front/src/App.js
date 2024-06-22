import React from 'react';
import './App.css';
import LivroForm from './components/LivroForm';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Cadastro de Livros</h1>
        <LivroForm />
      </header>
    </div>
  );
}

export default App;
