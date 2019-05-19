package hello;



import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GreetingController {

    @Autowired
    static Map<Long, Greeting> greetings = Collections.synchronizedMap(new HashMap<Long, Greeting>());

    @RequestMapping(value = "/tasks",method = RequestMethod.GET)
    public List<Greeting> greetingList() {
        List<Greeting> greeting=new ArrayList<Greeting>(greetings.values());
        return greeting;
    }

    @RequestMapping(value = "/tasks",method =RequestMethod.POST,produces = "application/json")
    public String cretae(@ModelAttribute  Greeting greeting){
        greetings.put(greeting.getId(), greeting);
        return "success";
    }

    @RequestMapping(value = "/tasks/{id}",method = RequestMethod.GET)
    public Greeting greeting(@PathVariable Long id){
        return greetings.get(id);
    }

    @RequestMapping(value = "/tasks/{id}",method =RequestMethod.DELETE)
    public String deleteGreeting(@PathVariable Long id) {
        greetings.remove(id);
        return "success";
    }
}
