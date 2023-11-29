package bubbles.springapibackend.api.controller.event.file;

import bubbles.springapibackend.domain.event.Event;
import bubbles.springapibackend.domain.event.EventInPerson;
import bubbles.springapibackend.domain.event.EventOnline;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileEventController {
    @Operation(summary = "Export File CSV", description = "Export event data as a CSV file.")
    @GetMapping("/export/csv")
    public void exportFileCSV(ListConfig<Event> list, @RequestParam String fileName) {
        FileWriter file = null;
        Formatter output = null;
        boolean error = false;

        fileName += ".csv";

        try {
            file = new FileWriter(fileName);
            output = new Formatter(file);
        } catch (IOException exception) {
            throw new RuntimeException("Erro ao abrir o arquivo", exception);
        }

        try {
            for (int i = 0; i < list.getSize(); i++) {
                Event event = list.getElement(i);
                if (event instanceof EventOnline eventOnline) {
                    output.format("%d;%s;%s;%s;%d;%s;%s;%s;%s\n", eventOnline.getId(),
                            eventOnline.getTitle(), eventOnline.getDate(), eventOnline.getCategory(),
                            eventOnline.getDuration(), eventOnline.getAuthor(), eventOnline.getBubble(),
                            eventOnline.getPlatform(), eventOnline.getUrl());
                } else if (event instanceof EventInPerson eventInPerson) {
                    output.format("%d;%s;%s;%s;%d;%s;%s;%b;%d;%s\n", eventInPerson.getId(),
                            eventInPerson.getTitle(), eventInPerson.getDate(), eventInPerson.getCategory(),
                            eventInPerson.getDuration(), eventInPerson.getAuthor(), eventInPerson.getBubble(),
                            eventInPerson.isPublicPlace(), eventInPerson.getPeopleCapacity(),
                            eventInPerson.getAddress());
                } else {
                    System.out.println("Tipo de arquivo não reconhecido");
                }
            }
        } catch (FormatterClosedException exception) {
            throw new RuntimeException("Erro ao gravar o arquivo", exception);
        } finally {
            output.close();
            try {
                file.close();
            } catch (IOException exception) {
                System.out.println("Erro ao fechar o arquivo");
                exception.printStackTrace();
                error = true;
            }

            if (error) {
                System.exit(1);
            }
        }
    }

    @Operation(summary = "Import File CSV", description = "Import a file CSV containing event data.")
    @PostMapping("/import/csv")
    public void importFileCSV(@RequestParam String fileName) {
        FileReader file = null;
        Scanner input = null;
        boolean error = false;

        fileName += ".csv";

        try {
            file = new FileReader(fileName);
            input = new Scanner(file).useDelimiter(";|\\n");
        } catch (FileNotFoundException exception) {
            System.out.println("Arquivo inexistente");
            System.exit(1);
        }

        try {
            System.out.printf("%-4S %-15S %-9S %-15S %-9S %-15S %-15S\n", "id", "titulo",
                    "data", "categoria", "duracao", "autor", "comunidade");
            while (input.hasNext()) {
                int id = input.nextInt();
                String title = input.next();
                String dateString = input.next();
                LocalDateTime date = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Double category = input.nextDouble();
                String duration = input.next();
                String author = input.next();
                String bubble = input.next();

                if (bubble.trim().equalsIgnoreCase("plataforma")) {
                    String platform = input.next();
                    String url = input.next();
                    System.out.printf("%04d %-15S %-9S %4.1f %-15S %-9S %-15S %-15S %-15S\n",
                            id, title, date, category, duration, author, bubble, platform,
                            url);
                } else if (bubble.trim().equalsIgnoreCase("localPublico")) {
                    boolean publicPlace = Boolean.parseBoolean(input.next());
                    Integer peopleCapacity = input.nextInt();
                    String address = input.next();
                    System.out.printf("%04d %-15S %-9S %4.1f %-15S %-9S %-15S %-15b %-9d %-15S\n",
                            id, title, date, category, duration, author, bubble, publicPlace,
                            peopleCapacity, address);
                } else {
                    System.out.println("Tipo de arquivo não reconhecido");
                }
            }
        } catch (NoSuchElementException | IllegalStateException exception) {
            System.out.println("Arquivo com problemas");
            exception.printStackTrace();
            error = true;
        } finally {
            input.close();
            try {
                file.close();
            } catch (IOException exception) {
                System.out.println("Erro ao fechar o arquivo");
                exception.printStackTrace();
                error = true;
            }

            if (error) {
                System.exit(1);
            }
        }
    }

    @Operation(summary = "Export File TXT", description = "Export event data as a TXT file.")
    @GetMapping("/export/txt")
    public void exportFileTXT(List<Event> list, @RequestParam String fileName) {
        int records = 0;

        String header = "00EVENTO";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        header += "01";

        gravaRegistro(header, fileName);

        for (Event event : list) {
            String body = "02";
            body += String.format("%-5d", event.getId());
            body += String.format("%-15s", event.getTitle());
            body += String.format("%-20s", event.getDate());
            body += String.format("%-20s", event.getCategory());
            body += String.format("%-5d", event.getDuration());
            body += String.format("%-15s", event.getAuthor());
            body += String.format("%-15s", event.getBubble());

            if (event instanceof EventOnline eventOnline) {
                body += String.format("%-15s", eventOnline.getPlatform());
                body += String.format("%-50s", eventOnline.getUrl());
            } else if (event instanceof EventInPerson eventInPerson) {
                body += String.format("%-4b", eventInPerson.isPublicPlace());
                body += String.format("%-5d", eventInPerson.getPeopleCapacity());
                body += String.format("%-30s", eventInPerson.getAddress());
            } else {
                System.out.println("Tipo de arquivo não reconhecido");
            }

            gravaRegistro(body, fileName);

            records++;
        }

        String trailer = "01";
        trailer += String.format("%010d",records);

        gravaRegistro(trailer, fileName);
    }

    @Operation(summary = "Import File TXT", description = "Import a file TXT containing event data.")
    @PostMapping("/import/txt")
    public void importFileTXT(@RequestParam String fileName) {
        BufferedReader input = null;
        String register;
        String typeRegister;
        int id;
        String title;
        LocalDateTime date;
        String category;
        int duration;
        String author;
        String bubble;
        int recordsRead = 0;
        int recordsRecorded;
        String platform;
        String url;
        boolean publicPlace;
        int peopleCapacity;
        String address;
        List<Event> listOutput = new ArrayList<>();

        try {
            input = new BufferedReader(new FileReader(fileName));
        }
        catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo");
        }

        try {
            register = input.readLine();

            while (register != null) {
                typeRegister = register.substring(0,2);
                if (typeRegister.equals("00")) {
                    System.out.println("Registro de Header");
                    System.out.println("Tipo de arquivo: " + register.substring(2, 6));
                    System.out.println("Ano e semestre: " + register.substring(6, 11));
                    System.out.println("Data e hora de gravação do arquivo: " + register.substring(11, 30));
                    System.out.println("Versão do documento: " + register.substring(30, 32));
                } else if (typeRegister.equals("01")) {
                    System.out.println("Eh um registro de trailer");
                    recordsRecorded = Integer.parseInt(register.substring(2, 12));
                    if (recordsRecorded == recordsRead) {
                        System.out.println("Quantidade de reg de dados gravados é compatível com a quantidade de reg de dados lidos");
                    }
                    else {
                        System.out.println("Quantidade de reg de dados gravados é incompatível com a quantidade de reg de dados lidos");
                    }
                }
                else if (typeRegister.equals("02")) {
                    System.out.println("Registro de Corpo");
                    id = Integer.parseInt(register.substring(2, 7).trim());
                    title = register.substring(7, 15).trim();
                    date = LocalDateTime.parse(register.substring(15, 34).trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
                    category = register.substring(34, 44).trim();
                    duration = Integer.parseInt(register.substring(44, 49).trim());
                    author = register.substring(49, 99).trim();
                    bubble = register.substring(99, 139).trim();

                    if (bubble.trim().equalsIgnoreCase("plataforma")) {
                        platform = register.substring(139, 154).trim();
                        url = register.substring(154, 189).trim();
                        EventOnline eventOnline = new EventOnline(id, title, date, category, duration, author, bubble, platform, url);
                        listOutput.add(eventOnline);
                    } else if (bubble.trim().equalsIgnoreCase("localPublico")) {
                        publicPlace = Boolean.parseBoolean(register.substring(139, 140).trim());
                        peopleCapacity = Integer.parseInt(register.substring(140, 145).trim());
                        address = register.substring(145, 195).trim();
                        EventInPerson eventInPerson = new EventInPerson(id, title, date, category, duration, author, bubble, publicPlace, peopleCapacity, address);
                        listOutput.add(eventInPerson);
                    } else {
                        System.out.println("Tipo de arquivo não reconhecido");
                    }

                    recordsRead++;
                }
                else {
                    System.out.println("Eh um registro inválido");
                }

                register = input.readLine();
            }

            input.close();
        }
        catch (IOException exception) {
            System.out.println("Erro ao ler o arquivo");
            exception.printStackTrace();
        }

        System.out.println("\nLista lida:");
        for (Event event : listOutput) {
            System.out.println(event);
        }
    }

    public static void gravaRegistro(String register, String fileName) {
        BufferedWriter output = null;

        try {
            output = new BufferedWriter(new FileWriter(fileName, true));
        } catch (IOException exception) {
            System.out.println("Erro ao abrir o arquivo");
        }

        try {
            output.append(register + "\n");
            output.close();
        } catch (IOException exception) {
            System.out.println("Erro ao gravar o arquivo");
            exception.printStackTrace();
        }
    }
}
