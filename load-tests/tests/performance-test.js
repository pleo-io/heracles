import { group, sleep, check, fail } from 'k6';
import http from 'k6/http';
import { Rate } from 'k6/metrics';



export let options = {
    vus: 1,
    duration: "1m"
}


export default function() {
    let req, res, headers;
    group('Marketing page requests', () => {
        
        let url = 'https://www.google.com';

        res = http.get(url, params);
        check(res, {
            "status code is 200": (res) => res.status === 200,
        });
        sleep(1);
    });
}