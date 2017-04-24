import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Customer } from './customer.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class CustomerService {

    private resourceUrl = 'api/customers';
    private resourceSearchUrl = 'api/customers/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(customer: Customer): Observable<Customer> {
        const copy: Customer = Object.assign({}, customer);
        copy.dateOfBirth = this.dateUtils
            .convertLocalDateToServer(customer.dateOfBirth);
        copy.created_date = this.dateUtils
            .convertLocalDateToServer(customer.created_date);
        copy.last_modified_date = this.dateUtils
            .convertLocalDateToServer(customer.last_modified_date);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(customer: Customer): Observable<Customer> {
        const copy: Customer = Object.assign({}, customer);
        copy.dateOfBirth = this.dateUtils
            .convertLocalDateToServer(customer.dateOfBirth);
        copy.created_date = this.dateUtils
            .convertLocalDateToServer(customer.created_date);
        copy.last_modified_date = this.dateUtils
            .convertLocalDateToServer(customer.last_modified_date);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Customer> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.dateOfBirth = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dateOfBirth);
            jsonResponse.created_date = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.created_date);
            jsonResponse.last_modified_date = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.last_modified_date);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<Response> {
        const options = this.createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res))
        ;
    }


    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].dateOfBirth = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dateOfBirth);
            jsonResponse[i].created_date = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].created_date);
            jsonResponse[i].last_modified_date = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].last_modified_date);
        }
        res._body = jsonResponse;
        return res;
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
