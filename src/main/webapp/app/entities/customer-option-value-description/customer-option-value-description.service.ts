import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CustomerOptionValueDescription } from './customer-option-value-description.model';
@Injectable()
export class CustomerOptionValueDescriptionService {

    private resourceUrl = 'api/customer-option-value-descriptions';
    private resourceSearchUrl = 'api/customer-option-value-descriptions/_search';

    constructor(private http: Http) { }

    create(customerOptionValueDescription: CustomerOptionValueDescription): Observable<CustomerOptionValueDescription> {
        const copy: CustomerOptionValueDescription = Object.assign({}, customerOptionValueDescription);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(customerOptionValueDescription: CustomerOptionValueDescription): Observable<CustomerOptionValueDescription> {
        const copy: CustomerOptionValueDescription = Object.assign({}, customerOptionValueDescription);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CustomerOptionValueDescription> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
        ;
    }
    private createRequestOption(req?: any): BaseRequestOptions {
        const options: BaseRequestOptions = new BaseRequestOptions();
        if (req) {
            const params: URLSearchParams = new URLSearchParams();
            params.set('page', req.page);
            params.set('size', req.size);
            if (req.sort) {
                params.paramsMap.set('sort', req.sort);
            }
            params.set('query', req.query);

            options.search = params;
        }
        return options;
    }
}
