package practica04;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;

import com.itextpdf.text.DocumentException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import nominas.entity.Categorias;
import nominas.entity.Empresas;
import nominas.entity.Nomina;
import nominas.entity.Trabajadorbbdd;

public class Nomina_Sistema {
    
    Row currentRow;
    private Lectura lectura;
    private GeneracionPDF generadorPDF;
    private ArrayList<Row> listaDefinitiva;
    private ArrayList<Row> listaDefinitivaSeg;
    
    @SuppressWarnings("unused")
    private int fila;
    
    static final int CANTIDAD_DIAS = 30;
    
    private double salarioBase;
    private double prorrata;
    private double complemento;
    private double antiguedad;
    private double numeroTrienios;
    
    private double contingenciasGenerales;
    private double desempleo;
    private double cuotaFormacion;
    private double IRPF;
    
    private double contingenciasComunesEmp;
    private double desempleoEmpresario;
    private double formacionEmpresario;
    private double accidentesTrabajo;
    private double FOGASA;
    
    private double devengos;
    private double brutoAnual;
    private double brutoMensual;
    
    private String categoria;
    private String fechaAltaEmpresa;
    
    String mesAltaStr;
    String añoAltaStr;
    
    private Map<String, Double> map;
    private Map<String, String> mapDatosEmpresa;
    
    private int mesSolicitud;
    private int añoSolicitud;
    private double importeTrienios;
    
    private boolean prorrataExtra;
    private double importeProrrataExtra;
    private double importeDeduccionSSocial;
    private double importeDeduccionDesempleo;
    private double importeDeduccionFormacion;
    private double importeDeduccionIRPF;
    private double importeFOGASA;
    private double importeDesempleoEmpr;
    private double importeFormacionEmpr;
    private double importeContigenciasComunesEmp;
    private double importeAccidentes;
    private double liquidoAPercibir;
    private double costeTotalTrabajador;
    
    
    private ManageCategoria MC;
    private ManageEmpresa ME;
    private ManageNomina MN;
    private ManageTrabajador MT;
    
    public Nomina_Sistema(int mes, int año) {
        
        lectura = new Lectura();
        
        generadorPDF = new GeneracionPDF();
        
        lectura.leerFicheroPorFilas(0);
        
        listaDefinitiva = lectura.getListaDefinitiva();
        listaDefinitiva.remove(0);
        
        lectura.leerFicheroPorFilas(1);
        
        listaDefinitivaSeg = lectura.getListaDefinitivaSegun();
        listaDefinitivaSeg.remove(0);
        
        currentRow = null;
        fila = 0;
        
        salarioBase = 0.0;
        prorrata = 0.0;
        complemento = 0.0;
        antiguedad = 0.0;
        contingenciasGenerales = 0.0;
        desempleo = 0.0;
        cuotaFormacion = 0.0;
        IRPF = 0.0;
        devengos = 0.0;
        brutoAnual = 0.0;
        categoria = "";
        fechaAltaEmpresa = "";
        
        mesSolicitud = mes;
        añoSolicitud = año;
        
        map = new HashMap<String, Double>();
        mapDatosEmpresa = new HashMap<String, String>();
        
        numeroTrienios = 0;
        importeTrienios = 0;
        prorrataExtra = false;
        brutoMensual = 0;
        
        mesAltaStr = "";
        añoAltaStr = "";
        importeProrrataExtra = 0;
        importeDeduccionDesempleo = 0;
        importeDeduccionFormacion = 0;
        importeDeduccionIRPF = 0;
        importeDeduccionSSocial = 0;
        
        importeFOGASA = 0;
        importeDesempleoEmpr = 0;
        importeFormacionEmpr = 0;
        importeContigenciasComunesEmp = 0;
        importeAccidentes = 0;
        
        FOGASA = 0;
        desempleoEmpresario = 0;
        formacionEmpresario = 0;
        contingenciasComunesEmp = 0;
        accidentesTrabajo = 0;
        
        costeTotalTrabajador = 0;
        liquidoAPercibir = 0;
        
        
        MC = new ManageCategoria();
        ME = new ManageEmpresa();
        MT = new ManageTrabajador();
        MN = new ManageNomina();
    }
    
    public void getCategoria() {
        
        Iterator<Row> it = listaDefinitiva.iterator();
        String prorrataExtraStr = "";
        String nombreEmpresa = "";
        String CIFEmpresa = "";
        String nombreTrabajador = "";
        String primerApellidoTrabajador = "";
        String segundoApellidoTrabajador = "";
        String DNITrabajador = "";
        String mesSolicitudStr = getMes(mesSolicitud);
        String añoSolicitudStr = String.valueOf(añoSolicitud);
        String IBAN = "";
        String email = "";
        String codigoCuenta = "";
        
        
        while (it.hasNext()) {
            
            currentRow = it.next();
            
            categoria = currentRow.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            prorrataExtraStr = currentRow.getCell(13, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            fechaAltaEmpresa = currentRow.getCell(8, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            nombreEmpresa = currentRow.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            CIFEmpresa = currentRow.getCell(7, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            nombreTrabajador = currentRow.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            primerApellidoTrabajador = currentRow.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            segundoApellidoTrabajador = currentRow.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            DNITrabajador = currentRow.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            codigoCuenta = currentRow.getCell(14, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            IBAN = currentRow.getCell(16, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            email = currentRow.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            Date fechaAltaDate = null;
            Date fechaDefDate = null;
            try {
                
                fechaAltaDate = formatter.parse(fechaAltaEmpresa);

                
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
            
            
            
            
            
            SimpleDateFormat formatterII =new SimpleDateFormat("yyyy-MM-dd");
            
            String nuevaFecha = formatterII.format(fechaAltaDate);
            
            try {
                fechaDefDate = formatterII.parse(nuevaFecha);
            } catch (ParseException ex) {
                Logger.getLogger(Nomina_Sistema.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            mapDatosEmpresa.put("nombreEmpresa", nombreEmpresa);
            mapDatosEmpresa.put("CIFEmpresa", CIFEmpresa);
            mapDatosEmpresa.put("nombreTrabajador", nombreTrabajador);
            mapDatosEmpresa.put("primerApellidoTrabajador", primerApellidoTrabajador);
            mapDatosEmpresa.put("segundoApellidoTrabajador", segundoApellidoTrabajador);
            mapDatosEmpresa.put("DNITrabajador", DNITrabajador);
            mapDatosEmpresa.put("fechaAltaEmpresa", fechaAltaEmpresa);
            mapDatosEmpresa.put("mesSolicitudStr", mesSolicitudStr);
            mapDatosEmpresa.put("añoSolicitudStr", añoSolicitudStr);
            map.put("mesSolicitud", (double) mesSolicitud);
            map.put("añoSolicitud", (double) añoSolicitud);
            mapDatosEmpresa.put("IBAN", IBAN);
            mapDatosEmpresa.put("categoria", categoria);
            mapDatosEmpresa.put("email", email);
            mapDatosEmpresa.put("codigoCuenta", codigoCuenta);
            
            fechaAltaEmpresa = fechaAltaEmpresa.substring(3);
            fila = currentRow.getRowNum();
            mesAltaStr = fechaAltaEmpresa.substring(0, 3);
            añoAltaStr = fechaAltaEmpresa.substring(4);
            int mesAlta = getNumeroMes(mesAltaStr);
            int añoAlta = Integer.valueOf(añoAltaStr);
            
            if (prorrataExtraStr.equalsIgnoreCase("SI")) {
                prorrataExtra = true;
            } else {
                prorrataExtra = false;
            }
            
            /*Hibernate Empresa*/
            System.out.println("HIBERNATE EMPRESA");
            List listaEmpresa = ME.listaEmpresa();
            Empresas empresaADevolver = new Empresas(nombreEmpresa, CIFEmpresa);
            
            if(listaEmpresa.isEmpty()){
                empresaADevolver = ME.addEmpresa(nombreEmpresa, CIFEmpresa);
            }else{
                
                for(Iterator iterator = listaEmpresa.iterator(); iterator.hasNext();){
                    
                    Empresas empresa = (Empresas) iterator.next();
                    if(empresa.getCif().equalsIgnoreCase(CIFEmpresa)){ //ACTUALIZAMOS
                        
                        System.out.println("ACTUALIZA EMPRESA");
                        empresaADevolver = ME.updateEmpresa(empresa.getIdEmpresa(), nombreEmpresa,CIFEmpresa);
                        
                    }else{
                        
                        if(ME.existeEnBBDDEmpresa(CIFEmpresa) == false){
                            System.out.println("AÑADE EMPRESA");
                            
                            empresaADevolver = ME.addEmpresa(nombreEmpresa, CIFEmpresa);
                            break;
                        }else{
                            
                            if(empresa.getCif().equalsIgnoreCase(CIFEmpresa)){
                                
                                empresaADevolver.setIdEmpresa(empresa.getIdEmpresa());
                            }
                            
                            System.out.println("Este Registro ya se encuentra en la BBDD");
                            
                            
                        }
                        
                        
                    }
                    
                    
                    
                    
                    
                    
                }
                
                
                
            }
            
            
            /*-----------------*/
            
            switch (categoria) {
                case "Operador":
                    getValoresCategoria("Operador");
                    break;
                case "Auxiliar":
                    getValoresCategoria("Auxiliar");
                    
                    break;
                case "Administrativo":
                    getValoresCategoria("Administrativo");
                    
                    break;
                case "Jefe de sección":
                    getValoresCategoria("Jefe de sección");
                    
                    break;
                case "Jefe división":
                    getValoresCategoria("Jefe división");
                    
                    break;
                case "Programador":
                    getValoresCategoria("Programador");
                    
                    break;
                case "Analista":
                    getValoresCategoria("Analista");
                    break;
                case "Jefe de servicio":
                    getValoresCategoria("Jefe de servicio");
                    break;
                case "Limpiador":
                    getValoresCategoria("Limpiador");
                    break;
                case "Cocinero":
                    getValoresCategoria("Cocinero");
                    break;
                case "Cuidador":
                    getValoresCategoria("Cuidador");
                    break;
                case "Ordenanza":
                    getValoresCategoria("Ordenanza");
                    break;
                case "Calefactor":
                    getValoresCategoria("Calefactor");
                    break;
                case "Coordinador":
                    getValoresCategoria("Coordinador");
                    break;
                    
                default:
                    System.out.println("FALLO EN CATEGORIA");
                    break;
            }
            
            
            /*Hibernate Categorias*/
            
            System.out.println("EMPIEZA HIBERNATE CATEGORIA");
            List listaCategoBBDD = MC.listaCategoria();
            Categorias categoriaADevolver = new Categorias(mapDatosEmpresa.get("categoria"), map.get("salarioBase"), map.get("complemento"));
            
            if(listaCategoBBDD.isEmpty()){
                
                categoriaADevolver = MC.addCategoria(mapDatosEmpresa.get("categoria"), map.get("salarioBase"), map.get("complemento"));
                System.out.println("AÑADE CATEGORIA");
                
                
            }else{
                
                for(Iterator iterator = listaCategoBBDD.iterator(); iterator.hasNext();){
                    
                    Categorias categoria = (Categorias) iterator.next();
                    
                    if(categoria.getNombreCategoria().equalsIgnoreCase(mapDatosEmpresa.get("categoria"))){ //ACTUALIZAMOS
                        
                        System.out.println("ACTUALIZA CATEGORIA");
                        categoriaADevolver = MC.updateCategoria(categoria.getIdCategoria(), mapDatosEmpresa.get("categoria"), map.get("salarioBase"), map.get("complemento"));
                        
                    }else{
                        
                        if(MC.existeEnBBDDCategoria(mapDatosEmpresa.get("categoria")) == false){
                            System.out.println("AÑADE CATEGORIA");
                            
                            categoriaADevolver = MC.addCategoria(mapDatosEmpresa.get("categoria"), map.get("salarioBase"), map.get("complemento"));
                            break;
                        }else{
                            
                            if(categoria.getNombreCategoria().equalsIgnoreCase(mapDatosEmpresa.get("categoria"))){
                                categoriaADevolver.setIdCategoria(categoria.getIdCategoria());
                                System.out.println("Este Registro ya se encuentra en la BBDD");
                            }
                            
                            
                            
                        }
                        
                        
                    }
                }
                
                
            }
            
            
            
            
            redondearNumero(map);
            try {
                if (!DNITrabajador.isEmpty()) {
                    
                    
                   
                    
                    
                    /*HIBERNATE TRABAJADOR*/
                    
                    System.out.println("EMPIEZA HIBERNATE TRABAJADOR");
                    List listaTrabajador = MT.listaTrabajador();
                    Trabajadorbbdd trabajadorADevolver = new Trabajadorbbdd();
                    //Para comparar correctamente fechas las ponemos en el mismo formato.
                    
                    
                    if(listaTrabajador.isEmpty()){
                        System.out.println("AÑADE EN TRABAJADOR");
                        
                        trabajadorADevolver = MT.addTrabajador(categoriaADevolver, empresaADevolver, nombreTrabajador, primerApellidoTrabajador, segundoApellidoTrabajador, DNITrabajador, email, fechaDefDate, codigoCuenta, IBAN);
                    }else{
                        
                        for(Iterator iterator = listaTrabajador.iterator(); iterator.hasNext();){
                            Trabajadorbbdd trabajador = (Trabajadorbbdd) iterator.next();
                            
                            
                            
                            if(trabajador.getNombre().equalsIgnoreCase(nombreTrabajador) && trabajador.getNifnie().equalsIgnoreCase(DNITrabajador) &&  trabajador.getFechaAlta().toString().equalsIgnoreCase(nuevaFecha)){
                                System.out.println("ACTUALIZA EN TRABAJADOR");
                                
                                trabajadorADevolver = MT.updateTrabajador(trabajador.getIdTrabajador(),categoriaADevolver, empresaADevolver, nombreTrabajador, primerApellidoTrabajador, segundoApellidoTrabajador, DNITrabajador, email, fechaDefDate, codigoCuenta, IBAN);
                                
                                
                            }else{
                                
                                if(MT.existeEnBBDDTrabajador(nombreTrabajador, DNITrabajador, nuevaFecha) == false){
                                    System.out.println("AÑADE EN TRABAJADOR");
                                    
                                    trabajadorADevolver = MT.addTrabajador(categoriaADevolver, empresaADevolver, nombreTrabajador, primerApellidoTrabajador, segundoApellidoTrabajador, DNITrabajador, email, fechaDefDate, codigoCuenta, IBAN);
                                    break;
                                }else{
                                    if(trabajador.getNombre().equalsIgnoreCase(nombreTrabajador) && trabajador.getNifnie().equalsIgnoreCase(DNITrabajador) &&  trabajador.getFechaAlta().toString().equalsIgnoreCase(nuevaFecha)){
                                        trabajadorADevolver.setIdTrabajador(trabajador.getIdTrabajador());
                                        
                                    }
                                    System.out.println("Este Registro ya se encuentra en la BBDD");
                                }
                                
                                
                                
                            }
                            
                            
                        }
                        
                        
                    }
                    
                    
                    /*----------------------*/
                    
                    
                    
                    if (añoAlta < añoSolicitud) {
                        generadorPDF.generar(map, mapDatosEmpresa, prorrataExtra);
                        hibernateNominaYTrabajador(trabajadorADevolver, mesAlta, añoAlta);

                        if (prorrataExtra == false && (mesSolicitud == 6 || mesSolicitud == 12)) {
                            
                            getDeduccionesExtra();
                            redondearNumero(map);
                            
                            generadorPDF.generarExtra(map, mapDatosEmpresa, prorrataExtra);
                            hibernateNominaYTrabajador(trabajadorADevolver, mesAlta, añoAlta);
                            
                        }
                        
                    } else if (añoAlta == añoSolicitud) {
                        if (mesAlta <= mesSolicitud) {
                            generadorPDF.generar(map, mapDatosEmpresa, prorrataExtra);
                            hibernateNominaYTrabajador(trabajadorADevolver, mesAlta, añoAlta);
                            
                            if (prorrataExtra == false && (mesSolicitud == 6 || mesSolicitud == 12)) {
                                
                                getDeduccionesExtra();
                                redondearNumero(map);
                                
                                generadorPDF.generarExtra(map, mapDatosEmpresa, prorrataExtra);
                                hibernateNominaYTrabajador(trabajadorADevolver, mesAlta, añoAlta);
                                
                            }
                            
                        }
                        
                        
                    }
                    
                    
                    
                    
                    
                } else {
                    System.out.println("El trabajador no tiene DNI.");
                }
            } catch (DocumentException e) {
                //e.printStackTrace();
            }
            
            map.clear();
            
        }
        
    }
    
    public void getValoresCategoria(String categoriaHojaPrim) {
        
        Iterator<Row> itSegunda = listaDefinitivaSeg.iterator();
        String salarioBaseStr = "";
        String complementoStr = "";
        
        while (itSegunda.hasNext()) {
            
            currentRow = itSegunda.next();
            
            categoria = currentRow.getCell(0, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            
            fila = currentRow.getRowNum();
            
            if (categoriaHojaPrim.equalsIgnoreCase(categoria)) {
                salarioBaseStr = currentRow.getCell(1, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                salarioBase = Double.valueOf(salarioBaseStr);
                
                complementoStr = currentRow.getCell(2, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                complemento = Double.valueOf(complementoStr);
                
            }
            
        }
        
        
        
        getCuotas();
        getNumeroTrienios(mesSolicitud, añoSolicitud, fechaAltaEmpresa);
        importeTrienios = getImporteTrienio(map.get("numeroTrienios"));
        
        antiguedad = importeTrienios * 14;
        
        
        
        map.put("contingenciasGenerales", contingenciasGenerales);
        map.put("desempleo", desempleo);
        map.put("cuotaFormacion", cuotaFormacion);
        map.put("salarioBase", salarioBase);
        map.put("complemento", complemento);
        map.put("devengos", devengos);
        map.put("importeTrienios", importeTrienios);
        map.put("antiguedad", antiguedad);
        map.put("contigenciasComunesEmp", contingenciasComunesEmp);
        map.put("FOGASA", FOGASA);
        map.put("desempleoEmpresario", desempleoEmpresario);
        map.put("formacionEmpresario", formacionEmpresario);
        map.put("accidentesTrabajo", accidentesTrabajo);
        
        map.put("salarioBaseDevengo", salarioBase / 14);
        map.put("complementoDevengo", complemento / 14);
        map.put("antiguedadDevengo", antiguedad / 14);
        
        brutoAnual = getSalarioBrutoAnual();
        getIRPF(brutoAnual);
        
        salarioBase /= 14;
        complemento /= 14;
        antiguedad /= 14;
        
        if (prorrataExtra == true) { // PRORRATEO
            
            // revisar para el mismo caso del else
            brutoMensual = salarioBase + complemento + antiguedad;
            prorrata = brutoMensual / 6;
            
            brutoMensual += prorrata;
            
        } else { // SIN PRORRATEO
            // controlar mes de junio y diciembre
            brutoMensual = salarioBase + complemento + antiguedad;
            prorrata = brutoMensual / 6;
            
            añoAltaStr = fechaAltaEmpresa.substring(4);
            int añoAlta = Integer.valueOf(añoAltaStr);
            
            if (añoSolicitud == añoAlta) {
                mesAltaStr = fechaAltaEmpresa.substring(0, 3);
                int mesAlta = getNumeroMes(mesAltaStr);
                int diferenciaMeses = mesSolicitud - mesAlta;
                
                importeProrrataExtra = prorrata * diferenciaMeses;
                
            } else {
                
                importeProrrataExtra = prorrata * 6;
                
            }
            
        }
        
        getDeducciones();
        
        map.put("prorrata", prorrata);
        map.put("importeProrrataExtra", importeProrrataExtra);
        map.put("brutoMensual", brutoMensual);
        
        map.put("brutoAnual", brutoAnual);
        map.put("IRPF", IRPF);
        
    }
    
    public void getDeducciones() {
        double baseReguladora = brutoMensual;
        double totalDeduccionesTrabajador = 0;
        double totalDeduccionesEmpresario = 0;
        
        if (prorrataExtra == false) {
            baseReguladora = brutoMensual + prorrata;
        }
        
        importeDeduccionSSocial = baseReguladora * (contingenciasGenerales / 100);
        importeDeduccionDesempleo = baseReguladora * (desempleo / 100);
        importeDeduccionIRPF = brutoMensual * (IRPF / 100);
        importeDeduccionFormacion = baseReguladora * (cuotaFormacion / 100);
        importeFOGASA = baseReguladora * (FOGASA / 100);
        importeAccidentes = baseReguladora * (accidentesTrabajo / 100);
        importeContigenciasComunesEmp = baseReguladora * (contingenciasComunesEmp / 100);
        importeDesempleoEmpr = baseReguladora * (desempleoEmpresario / 100);
        importeFormacionEmpr = baseReguladora * (formacionEmpresario / 100);
        
        totalDeduccionesEmpresario = importeFOGASA + importeAccidentes + importeContigenciasComunesEmp
                + importeDesempleoEmpr + importeFormacionEmpr;
        
        totalDeduccionesTrabajador = importeDeduccionDesempleo + importeDeduccionSSocial + importeDeduccionIRPF
                + importeDeduccionFormacion;
        
        liquidoAPercibir = brutoMensual - totalDeduccionesTrabajador;
        costeTotalTrabajador = brutoMensual + totalDeduccionesEmpresario;
        
        map.put("baseReguladora", baseReguladora);
        map.put("importeSeguridadSocial", importeDeduccionSSocial);
        map.put("importeDesempleo", importeDeduccionDesempleo);
        map.put("importeIRPF", importeDeduccionIRPF);
        map.put("importeFormacion", importeDeduccionFormacion);
        map.put("importeFOGASA", importeFOGASA);
        map.put("importeAccidentes", importeAccidentes);
        map.put("importeContigenciasComunesEmp", importeContigenciasComunesEmp);
        map.put("importeDesempleoEmpr", importeDesempleoEmpr);
        map.put("importeFormacionEmpr", importeFormacionEmpr);
        map.put("totalDeduccionesTrabajador", totalDeduccionesTrabajador);
        map.put("totalDeduccionesEmpresario", totalDeduccionesEmpresario);
        map.put("liquidoAPercibir", liquidoAPercibir);
        map.put("costeTotalTrabajador", costeTotalTrabajador);
    }
    
    public void getDeduccionesExtra() {
        
        double baseReguladora = brutoMensual;
        double totalDeduccionesTrabajador = 0;
        double totalDeduccionesEmpresario = 0;
        
        if (prorrataExtra == false) {
            baseReguladora = brutoMensual + prorrata;
        }
        
        importeDeduccionSSocial = baseReguladora * (contingenciasGenerales / 100);
        importeDeduccionDesempleo = baseReguladora * (desempleo / 100);
        importeDeduccionIRPF = brutoMensual * (IRPF / 100);
        importeDeduccionFormacion = baseReguladora * (cuotaFormacion / 100);
        importeFOGASA = baseReguladora * (FOGASA / 100);
        importeAccidentes = baseReguladora * (accidentesTrabajo / 100);
        importeContigenciasComunesEmp = baseReguladora * (contingenciasComunesEmp / 100);
        importeDesempleoEmpr = baseReguladora * (desempleoEmpresario / 100);
        importeFormacionEmpr = baseReguladora * (formacionEmpresario / 100);
        
        totalDeduccionesEmpresario = importeFOGASA + importeAccidentes + importeContigenciasComunesEmp
                + importeDesempleoEmpr + importeFormacionEmpr;
        
        costeTotalTrabajador = brutoMensual + totalDeduccionesEmpresario;
        
        if (prorrataExtra == false && (mesSolicitud == 6 || mesSolicitud == 12)) {
            baseReguladora = 0;
            importeDeduccionSSocial = 0;
            importeDeduccionDesempleo = 0;
            importeDeduccionFormacion = 0;
            importeFOGASA = 0;
            importeAccidentes = 0;
            importeContigenciasComunesEmp = 0;
            importeDesempleoEmpr = 0;
            importeFormacionEmpr = 0;
            
            totalDeduccionesEmpresario = 0;
            costeTotalTrabajador = brutoMensual;
            
        }
        
        totalDeduccionesTrabajador = importeDeduccionDesempleo + importeDeduccionSSocial + importeDeduccionIRPF
                + importeDeduccionFormacion;
        
        liquidoAPercibir = brutoMensual - totalDeduccionesTrabajador;
        
        map.put("baseReguladora", baseReguladora);
        map.put("importeSeguridadSocial", importeDeduccionSSocial);
        map.put("importeDesempleo", importeDeduccionDesempleo);
        map.put("importeIRPF", importeDeduccionIRPF);
        map.put("importeFormacion", importeDeduccionFormacion);
        map.put("importeFOGASA", importeFOGASA);
        map.put("importeAccidentes", importeAccidentes);
        map.put("importeContigenciasComunesEmp", importeContigenciasComunesEmp);
        map.put("importeDesempleoEmpr", importeDesempleoEmpr);
        map.put("importeFormacionEmpr", importeFormacionEmpr);
        map.put("totalDeduccionesTrabajador", totalDeduccionesTrabajador);
        map.put("totalDeduccionesEmpresario", totalDeduccionesEmpresario);
        map.put("liquidoAPercibir", liquidoAPercibir);
        map.put("costeTotalTrabajador", costeTotalTrabajador);
        
    }
    
    public void getCuotas() {
        
        Iterator<Row> itSegunda = listaDefinitivaSeg.iterator();
        String contingenciasGeneralesStr = "";
        String desempleoStr = "";
        String formacion = "";
        String contingenciasEmprStr = "";
        String accidentesTrabajoStr = "";
        String FOGASAStr = "";
        String desempleoEmprStr = "";
        String formacionEmpStr = "";
        
        contingenciasGeneralesStr = itSegunda.next().getSheet().getRow(17).getCell(1).toString(); 

        desempleoStr = itSegunda.next().getSheet().getRow(18).getCell(1).toString();
        formacion = itSegunda.next().getSheet().getRow(19).getCell(1).toString();
        contingenciasEmprStr = itSegunda.next().getSheet().getRow(20).getCell(1).toString();
        FOGASAStr = itSegunda.next().getSheet().getRow(21).getCell(1).toString();
        desempleoEmprStr = itSegunda.next().getSheet().getRow(22).getCell(1).toString();
        formacionEmpStr = itSegunda.next().getSheet().getRow(23).getCell(1).toString();
        accidentesTrabajoStr = itSegunda.next().getSheet().getRow(24).getCell(1).toString();
        
        contingenciasGenerales = Double.valueOf(contingenciasGeneralesStr);
        desempleo = Double.valueOf(desempleoStr);
        cuotaFormacion = Double.valueOf(formacion);
        contingenciasComunesEmp = Double.valueOf(contingenciasEmprStr);
        FOGASA = Double.valueOf(FOGASAStr);
        desempleoEmpresario = Double.valueOf(desempleoEmprStr);
        formacionEmpresario = Double.valueOf(formacionEmpStr);
        accidentesTrabajo = Double.valueOf(accidentesTrabajoStr);
        
    }
    
    public void redondearNumero(Map<String, Double> map) {
        
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            Object key = it.next();
            map.put(key.toString(), Math.round(map.get(key) * 100.0) / 100.0);
            // System.out.println("Clave: " + key + " -> Valor: " + map.get(key));
            
        }
    }
    
    public void getIRPF(double brutoAnual) {
        
        Iterator<Row> it = listaDefinitivaSeg.iterator();
        Iterator<Row> itNext = listaDefinitivaSeg.iterator();
        Row next = null;
        double brutoAnualHoja = 0.0;
        double nextBrutoAnualHoja = 0.0;
        String brutoAnualHojaStr = "";
        String nextBrutoAnualHojaStr = "";
        String IRPFStr = "";
        
        itNext.next();
        
        while (it.hasNext()) {
            
            currentRow = it.next();
            if (itNext.hasNext()) {
                next = itNext.next();
            }
            
            brutoAnualHojaStr = currentRow.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            brutoAnualHoja = Double.valueOf(brutoAnualHojaStr);
            nextBrutoAnualHojaStr = next.getCell(5, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            nextBrutoAnualHoja = Double.valueOf(nextBrutoAnualHojaStr);
            
            if (brutoAnual < 12000) {
                IRPF = 0;
                
            } else if (brutoAnual == brutoAnualHoja) {
                IRPFStr = currentRow.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                IRPF = Double.valueOf(IRPFStr);
                
            } else if (brutoAnualHoja < brutoAnual && brutoAnual < nextBrutoAnualHoja) {
                
                IRPFStr = next.getCell(6, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
                IRPF = Double.valueOf(IRPFStr);
                
            }
        }
        
    }
    
    public void getNumeroTrienios(int mesSolicitud, int añoSolicitud, String fechaAltaEmpresa) {
        
        int diferencia = 0;
        mesAltaStr = fechaAltaEmpresa.substring(0, 3);
        añoAltaStr = fechaAltaEmpresa.substring(4);
        int añoAlta = Integer.valueOf(añoAltaStr);
        int mesAlta = getNumeroMes(mesAltaStr);
        
        diferencia = añoSolicitud - añoAlta;
        int resto = diferencia % 3;
        numeroTrienios = diferencia / 3;
        if (resto == 0) {
            
            numeroTrienios -= 1;
            
            // De cara a calcular en el metodo getSalarioBrutoAnual();
            
            map.put("mesAlta", (double) mesAlta);
        }
        
        if (numeroTrienios < 0) {
            numeroTrienios = 0;
        }
        
        map.put("numeroTrienios", numeroTrienios);
        
    }
    
    private double getImporteTrienio(double numeroTrienios) {
        
        Iterator<Row> itSegunda = listaDefinitivaSeg.iterator();
        String cantidadTrieniosStr = "";
        String importeTrieniosStr = "";
        double cantidadTrienios = 0;
        double importeTrienios = 0;
        int numeroTrieniosAux = (int) numeroTrienios;
        int cantidadTrieniosAux = 0;
        
        for (int i = 0; i < 17; i++) {
            currentRow = itSegunda.next();
            
        }
        
        while (itSegunda.hasNext()) {
            
            currentRow = itSegunda.next();
            
            cantidadTrieniosStr = currentRow.getCell(3, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            importeTrieniosStr = currentRow.getCell(4, MissingCellPolicy.CREATE_NULL_AS_BLANK).toString();
            fila = currentRow.getRowNum();
            if (!cantidadTrieniosStr.isEmpty()) {
                
                cantidadTrienios = Double.valueOf(cantidadTrieniosStr);
                cantidadTrieniosAux = (int) cantidadTrienios;
                
                if (numeroTrieniosAux == cantidadTrieniosAux) {
                    importeTrienios = Double.valueOf(importeTrieniosStr);
                }
            }
            
        }
        
        return importeTrienios;
    }
    
    private int getNumeroMes(String mesAltaStr) {
        
        int mesAlta = 0;
        
        switch (mesAltaStr) {
            case "ene":
                mesAlta = 1;
                break;
            case "feb":
                mesAlta = 2;
                break;
            case "mar":
                mesAlta = 3;
                break;
            case "abr":
                mesAlta = 4;
                break;
            case "may":
                mesAlta = 5;
                break;
            case "jun":
                mesAlta = 6;
                break;
            case "jul":
                mesAlta = 7;
                break;
            case "ago":
                mesAlta = 8;
                break;
            case "sep":
                mesAlta = 9;
                break;
            case "oct":
                mesAlta = 10;
                break;
            case "nov":
                mesAlta = 11;
                break;
            case "dic":
                mesAlta = 12;
                break;
                
            default:
                break;
        }
        
        return mesAlta;
        
    }
    
    private String getMes(int numeroMes) {
        
        String mes = "";
        
        switch (numeroMes) {
            case 1:
                mes = "Enero";
                break;
            case 2:
                mes = "Febrero";
                break;
            case 3:
                mes = "Marzo";
                break;
            case 4:
                mes = "Abril";
                break;
            case 5:
                mes = "Mayo";
                break;
            case 6:
                mes = "Junio";
                break;
            case 7:
                mes = "Julio";
                break;
            case 8:
                mes = "Agosto";
                break;
            case 9:
                mes = "Septiembre";
                break;
            case 10:
                mes = "Octubre";
                break;
            case 11:
                mes = "Noviembre";
                break;
            case 12:
                mes = "Diciembre";
                break;
                
            default:
                break;
        }
        
        return mes;
        
    }
    
    public double getSalarioBrutoAnual() {
        
        int mesAlta;
        int añoAltaPr = Integer.valueOf(añoAltaStr);
        int mesAltaPr = getNumeroMes(mesAltaStr);
        double mesesAux = 12 - mesAltaPr + 1;
        double mesExtraJunio = 0;
        double mesExtraDic = 0;
        
        double salarioBrutoAnual = 0;
        int mesesTotales = 14;
        
        double salarioBase = map.get("salarioBase");
        double complemento = map.get("complemento");
        double importeTrienio = map.get("importeTrienios");
        double cantidadTrienioSig = map.get("numeroTrienios") + 1;
        double importeTrienioSig = getImporteTrienio(cantidadTrienioSig);
        
        // Trienios
        
        if (map.get("mesAlta") == null) {
            
            mesAlta = 0;
        } else {
            
            mesAlta = map.get("mesAlta").intValue();
        }
        
        if (añoSolicitud == añoAltaPr) // recien contratado
        {
            if (prorrataExtra == false) {// sin prorrateo
                
                // Meses reales trabajados
                
                // EXTRAS QUE TIENE QUE COBRAR
                
                // Junio
                if (mesAltaPr < 6) {
                    mesExtraJunio = 6 - mesAltaPr;
                }
                
                // Diciembre
                
                mesExtraDic = 12 - mesAltaPr;
                
                if (mesExtraDic > 6) {
                    
                    mesExtraDic = 6;
                }
                
                salarioBrutoAnual = (((salarioBase + complemento) / 14) * mesesAux)
                        + (((salarioBase + complemento) / 14) * (mesExtraJunio / 6))
                        + (((salarioBase + complemento) / 14) * (mesExtraDic / 6));
                
            } else {// con prorrateo
                
                salarioBrutoAnual = (salarioBase + complemento) * (mesesAux / 12);
                
            }
            
        } else {
            
            // Cambio de trienio
            if (mesAlta != 0) {
                if (mesAlta < 6) {
                    salarioBrutoAnual = salarioBase + complemento + (importeTrienio * mesAlta)
                            + (importeTrienioSig * (12 - mesAlta)) + (importeTrienioSig * 2);
                } else if (mesAlta >= 6 && mesAlta < 12) {
                    
                    salarioBrutoAnual = salarioBase + complemento + (importeTrienio * mesAlta)
                            + (importeTrienioSig * (12 - mesAlta)) + importeTrienioSig;
                    
                } else if (mesAlta == 12) {
                    
                    salarioBrutoAnual = salarioBase + complemento + (importeTrienio * mesAlta)
                            + (importeTrienioSig * (12 - mesAlta));
                }
                // Sin cambio de trienio
            } else {
                salarioBrutoAnual = salarioBase + complemento + (importeTrienio * mesesTotales);
                
            }
            
        }
        
        return salarioBrutoAnual;
    }
    
    private void hibernateNominaYTrabajador(Trabajadorbbdd trabajadorADevolver, int mesAlta, int añoAlta){
        /*HIBERNATE NOMINA*/
        
        System.out.println("EMPIEZA HIBERNATE NOMINA");
        List listaNominaBBDD = MN.listaNomina();
        
        if(listaNominaBBDD.isEmpty()){
            
            MN.addNomina(trabajadorADevolver, mesAlta, añoAlta, (int) numeroTrienios, importeTrienios, map.get("salarioBaseDevengo"), map.get("complementoDevengo"), prorrata, brutoAnual, IRPF, importeDeduccionIRPF, map.get("baseReguladora"),contingenciasComunesEmp , importeContigenciasComunesEmp, desempleoEmpresario, importeDesempleoEmpr, formacionEmpresario, importeFormacionEmpr, accidentesTrabajo, importeAccidentes, FOGASA, importeFOGASA, contingenciasGenerales, importeDeduccionSSocial, desempleo, importeDeduccionDesempleo, cuotaFormacion, importeDeduccionFormacion, brutoMensual, liquidoAPercibir, costeTotalTrabajador);
            System.out.println("AÑADE NOMINA");

        }else{
            
            for(Iterator iterator = listaNominaBBDD.iterator(); iterator.hasNext();){
                
                Nomina nomina = (Nomina)iterator.next();
                
                if(nomina.getMes() == mesAlta && nomina.getAnio() == añoAlta && nomina.getTrabajadorbbdd().getIdTrabajador().equals(trabajadorADevolver.getIdTrabajador()) && nomina.getBrutoNomina() == brutoMensual && nomina.getLiquidoNomina() == liquidoAPercibir){
                    
                    MN.updateNomina(nomina.getIdNomina(),trabajadorADevolver, mesAlta, añoAlta, (int) numeroTrienios, importeTrienios, map.get("salarioBaseDevengo"), map.get("complementoDevengo"), prorrata, brutoAnual, IRPF, importeDeduccionIRPF, map.get("baseReguladora"),contingenciasComunesEmp , importeContigenciasComunesEmp, desempleoEmpresario, importeDesempleoEmpr, formacionEmpresario, importeFormacionEmpr, accidentesTrabajo, importeAccidentes, FOGASA, importeFOGASA, contingenciasGenerales, importeDeduccionSSocial, desempleo, importeDeduccionDesempleo, cuotaFormacion, importeDeduccionFormacion, brutoMensual, liquidoAPercibir, costeTotalTrabajador);
                    System.out.println("ACTUALIZA NOMINA");
                    
                }else{
                    
                    if(MN.existeEnBBDDNomina(mesAlta, añoAlta, trabajadorADevolver, brutoMensual, liquidoAPercibir) == false){
                        MN.addNomina(trabajadorADevolver, mesAlta, añoAlta, (int) numeroTrienios, importeTrienios, map.get("salarioBaseDevengo"), map.get("complementoDevengo"), prorrata, brutoAnual, IRPF, importeDeduccionIRPF, map.get("baseReguladora"),contingenciasComunesEmp , importeContigenciasComunesEmp, desempleoEmpresario, importeDesempleoEmpr, formacionEmpresario, importeFormacionEmpr, accidentesTrabajo, importeAccidentes, FOGASA, importeFOGASA, contingenciasGenerales, importeDeduccionSSocial, desempleo, importeDeduccionDesempleo, cuotaFormacion, importeDeduccionFormacion, brutoMensual, liquidoAPercibir, costeTotalTrabajador);
                        System.out.println("AÑADE NOMINA");
                        break;
                    }else{
                        System.out.println("Este Registro ya se encuentra en la BBDD");
                        
                    }
                    
                    
                }
                
            }
            
        }
        
        
        
        /*-----------------*/
    }
    
}
