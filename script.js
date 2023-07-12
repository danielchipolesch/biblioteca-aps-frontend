/*
    Função para obter lista de produtos existente no banco de dados, via método GET
*/
const getList = async () => {
  let url = "http://127.0.0.1:8080/api/v1/livros/buscar";
  fetch(url, {
    method: "get",
  })
    .then((response) => response.json())
    .then((jsonData) => {
      jsonData.forEach((item) =>
        insertList(
          item.id,
          item.titulo,
          item.autor,
          item.editora,
          item.publicacao,
          item.isbn
        )
      );
    })
    .catch((error) => {
      console.log("Error: ", error);
    });
};

// Chamando a função inicial para pegar lista de produtos da API
getList();

/* 
    Função para inserir novo produto, via método POST
*/
const novoItem = async () => {
  let url = "http://127.0.0.1:8080/api/v1/livros/salvar";
  let requestBody = {
    titulo: document.getElementById("titulo").value,
    autor: document.getElementById("autor").value,
    editora: document.getElementById("editora").value,
    publicacao: document.getElementById("publicacao").value,
    isbn: document.getElementById("isbn").value,
  };

  if (requestBody.titulo == "") {
    alert("Escreva o nome do titulo!");
  } else if (requestBody.autor == "") {
    alert("Escreva o nome do autor!");
  } else if (requestBody.editora == "") {
    alert("Escreva o nome da Editora!");
  } else if (requestBody.publicacao == "") {
    alert("Insira a data de publicação!");
  } else if (requestBody.isbn == "") {
    alert("Digite o Isbn!");
  } else {
    fetch(url, {
      method: "post",
      body: JSON.stringify(requestBody), //converter objeto javascript em um objeto do tipo json
      headers: { "Content-Type": "application/json;" }, // informar para a API que o body é um json
    })
      .then((response) => response.json())
      .then((responseBody) => {
        insertList(
          responseBody.id,
          responseBody.titulo,
          responseBody.autor,
          responseBody.editora,
          responseBody.publicacao,
          requestBody.isbn
        ); //adicionar na tela
      })
      .catch((error) => {
        console.log("Error: ", error);
      });

    limparCamposInputTela(); // função para limpar as informações do campo de input da tela/interface
    alert("Item adicionado!");
  }
};

// Função auxiliar para limpar os inputs de texto da tela
const limparCamposInputTela = () => {
  document.getElementById("titulo").value = "";
  document.getElementById("autor").value = "";
  document.getElementById("editora").value = "";
  document.getElementById("publicacao").value = "";
  document.getElementById("isbn").value = "";
};

/* 
    Função que chama o endpoint de delete passando o id do produto para ser deletado no banco de dados, via método DELETE
*/
const deleteItem = (id) => {
  let url = "http://127.0.0.1:8080/api/v1/livros/deletar/" + id;
  fetch(url, {
    method: "delete",
  }).catch((error) => {
    console.log("Error: ", error);
  });
};

/* 
    Função para remover/deletar um item da lista de acordo com o click no botão/ícone "close" (x)
*/
const onClickEvent2RemoveElement = () => {
  let lista_de_close = document.getElementsByClassName("close"); // pegando a lista de todos os elementos que contém a classe close, ou seja, <span>'s

  for (var i = 0; i < lista_de_close.length; i++) {
    lista_de_close[i].onclick = function () {
      // adicionando um evento para "escutar" o click do usuário
      let row = this.parentElement.parentElement; // vou pegar o "avô" da <span>
      const idItem = row.getElementsByTagName("td")[0].innerHTML; // tô pegando o id do produto que será excluído
      if (
        confirm(
          "Você tem certeza que deseja excluir o produto " +
            row.getElementsByTagName("td")[1].innerHTML +
            "?"
        )
      ) {
        deleteItem(idItem); // função para chamar o método/endpoint de deletar
        row.remove(); // remove a linha da tabela na interface
        alert("Produto removido!");
      }
    };
  }
};

/* 
    Função auxiliar para adicionar um ícone "x" na última posição de cada linha da tabela
*/
const insertIconX = (indice) => {
  //let span = document.createElement("span"); // criando um novo elemento html do tipo span para colocar o conteúdo
  let button = document.createElement("a");
  //let txt = document.createTextNode("\u00D7"); // adicionado o símbolo "x" da multiplicação via código unicode
  button.innerHTML = `<button class=btn-trash><img class="trash" src="https://cdn-icons-png.flaticon.com/512/126/126468.png";></button>`;
  //let icon = document.createTextNode("https://cdn-icons-png.flaticon.com/512/126/126468.png");
  button.className = "close"; // atribuindo uma classe css ao meu mais novo span
  //button.appendChild(icon); // adicionar o nó "txt" à minha tag <span>
  indice.appendChild(button); // adicionar na última posição da minha linha a <span> com o conteúdo
};

/* 
    Função auxiliar para "desenhar" ou colocar os items na tela (um por um).
*/
const insertList = (id, titulo, autor, editora, publicacao, isbn) => {
  var item = [id, titulo, autor, editora, publicacao, isbn];
  var table = document.getElementById("myTable");
  var row = table.insertRow();

  // Colocando conteúdo na lista, produto a produto
  for (var i = 0; i < item.length; i++) {
    var celula = row.insertCell(i);
    celula.textContent = item[i];
  }
  insertIconX(row.insertCell(-1)); //função para adicionar um "x" na última célula/posição da linha

  onClickEvent2RemoveElement(); // função para adicionar um evento no ícone de remover, para remover  o elemento da linha que foi clicado
};

//https://cdn-icons-png.flaticon.com/512/126/126468.png
