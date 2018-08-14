package strapasson.leonardo.sbank_app.Repository;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import strapasson.leonardo.sbank_app.CadastroModel;
import strapasson.leonardo.sbank_app.DatabaseUtil;


public class cadastroRepository {

    DatabaseUtil databaseUtil;

    /***
     * CONSTRUTOR
     * @param context
     */
    public cadastroRepository(Context context){

        databaseUtil =  new DatabaseUtil(context);

    }

    /***
     * SALVA UM NOVO REGISTRO NA BASE DE DADOS
     * @param cadastroModel
     */
    public void Salvar(CadastroModel cadastroModel){

        ContentValues contentValues =  new ContentValues();
        /*MONTANDO OS PARAMETROS PARA SEREM SALVOS*/
        contentValues.put("nomecompleto",       cadastroModel.getNome());
        contentValues.put("email",   cadastroModel.getEmail());
        contentValues.put("senha",       cadastroModel.getSenha());
        contentValues.put("confirmasenha", cadastroModel.getConfirmaSenha());


        /*EXECUTANDO INSERT DE UM NOVO REGISTRO*/
        databaseUtil.GetConexaoDataBase().insert("tb_pessoa",null,contentValues);

    }

    /***
     * ATUALIZA UM REGISTRO JÁ EXISTENTE NA BASE
     * @param cadastroModel
     */
    public void Atualizar(CadastroModel cadastroModel){

        ContentValues contentValues =  new ContentValues();

        /*MONTA OS PARAMENTROS PARA REALIZAR UPDATE NOS CAMPOS*/
        contentValues.put("nomecompleto",       cadastroModel.getNome());
        contentValues.put("email",   cadastroModel.getEmail());
        contentValues.put("senha",       cadastroModel.getSenha());
        contentValues.put("confirmasenha", cadastroModel.getConfirmaSenha());


        /*REALIZANDO UPDATE PELA CHAVE DA TABELA*/
        databaseUtil.GetConexaoDataBase().update("tb_pessoa", contentValues, "id_pessoa = ?", new String[]{Integer.toString(cadastroModel.getCodigo())});
    }

    /***
     * EXCLUI UM REGISTRO PELO CÓDIGO
     * @param codigo
     * @return
     */
    public Integer Excluir(int codigo){

        //EXCLUINDO  REGISTRO E RETORNANDO O NÚMERO DE LINHAS AFETADAS
        return databaseUtil.GetConexaoDataBase().delete("tb_pessoa","id_pessoa = ?", new String[]{Integer.toString(codigo)});
    }

    /***
     * CONSULTA UMA PESSOA CADASTRADA PELO CÓDIGO
     * @param codigo
     * @return
     */
    public CadastroModel GetPessoa(int codigo){


        Cursor cursor =  databaseUtil.GetConexaoDataBase().rawQuery("SELECT * FROM tb_pessoa WHERE id_pessoa= "+ codigo,null);

        cursor.moveToFirst();

        ///CRIANDO UMA NOVA PESSOAS
        CadastroModel cadastroModel =  new CadastroModel();

        //ADICIONANDO OS DADOS DA PESSOA
        cadastroModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id")));
        cadastroModel.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        cadastroModel.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        cadastroModel.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
        cadastroModel.setConfirmaSenha(cursor.getString(cursor.getColumnIndex("confirmasenha")));


        //RETORNANDO A PESSOA
        return cadastroModel;

    }

    /***
     * CONSULTA TODAS AS PESSOAS CADASTRADAS NA BASE
     * @return
     */
    public List<CadastroModel> SelecionarTodos(){

        List<CadastroModel> pessoas = new ArrayList<CadastroModel>();


        //MONTA A QUERY A SER EXECUTADA
        StringBuilder stringBuilderQuery = new StringBuilder();
        stringBuilderQuery.append(" SELECT id     ");
        stringBuilderQuery.append("        nome,        ");
        stringBuilderQuery.append("        email,    ");
        stringBuilderQuery.append("        senha,        ");
        stringBuilderQuery.append("        confirmasenha,  ");
        stringBuilderQuery.append("  FROM  cadastroUsuario       ");
        stringBuilderQuery.append(" ORDER BY nome       ");


        //CONSULTANDO OS REGISTROS CADASTRADOS
        Cursor cursor = databaseUtil.GetConexaoDataBase().rawQuery(stringBuilderQuery.toString(), null);

        /*POSICIONA O CURSOR NO PRIMEIRO REGISTRO*/
        cursor.moveToFirst();


        CadastroModel cadastroModel;

        //REALIZA A LEITURA DOS REGISTROS ENQUANTO NÃO FOR O FIM DO CURSOR
        while (!cursor.isAfterLast()){

            /* CRIANDO UMA NOVA PESSOAS */
             cadastroModel =  new CadastroModel();

            //ADICIONANDO OS DADOS DA PESSOA
            cadastroModel.setCodigo(cursor.getInt(cursor.getColumnIndex("id")));
            cadastroModel.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            cadastroModel.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            cadastroModel.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            cadastroModel.setConfirmaSenha(cursor.getString(cursor.getColumnIndex("confirmasenha")));

            //ADICIONANDO UMA PESSOA NA LISTA
            pessoas.add(cadastroModel);

            //VAI PARA O PRÓXIMO REGISTRO
            cursor.moveToNext();
        }

        //RETORNANDO A LISTA DE PESSOAS
        return pessoas;

    }

}
