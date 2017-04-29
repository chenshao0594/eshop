import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ProductOption, ProductOptionValue } from './product-option.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ProductOptionService {

    private resourceUrl = 'api/product-options';
    private resourceSearchUrl = 'api/product-options/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(productOption: ProductOption): Observable<ProductOption> {
        const copy: ProductOption = Object.assign({}, productOption);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(productOption: ProductOption): Observable<ProductOption> {
        const copy: ProductOption = Object.assign({}, productOption);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ProductOption> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
//            jsonResponse.createdDate = this.dateUtils
//                .convertLocalDateFromServer(jsonResponse.createdDate);
//            jsonResponse.lastModifiedDate = this.dateUtils
//                .convertLocalDateFromServer(jsonResponse.lastModifiedDate);
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

    createOptionValue(id:number, productOptionValue: ProductOptionValue): Observable<ProductOptionValue> {
        const copy: ProductOptionValue = Object.assign({}, productOptionValue);
        return this.http.post('api/product-options/'+id+'/product-option-values', copy).map((res: Response) => {
            return res.json();
        });
    }

    private convertResponse(res: any): any {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            jsonResponse[i].createdDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].createdDate);
            jsonResponse[i].lastModifiedDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].lastModifiedDate);
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
