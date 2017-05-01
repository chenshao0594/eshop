import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Product } from './product.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ProductService {

    private resourceUrl = 'api/products';
    private resourceSearchUrl = 'api/products/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(product: Product): Observable<Product> {
        const copy: Product = Object.assign({}, product);
        copy.dateAvailable = this.dateUtils
            .convertLocalDateToServer(product.dateAvailable);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(product: Product): Observable<Product> {
        const copy: Product = Object.assign({}, product);
        copy.dateAvailable = this.dateUtils
            .convertLocalDateToServer(product.dateAvailable);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Product> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.dateAvailable = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.dateAvailable);
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
            jsonResponse[i].dateAvailable = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].dateAvailable);
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
    
    addOptionValue(productId:number ,optionIds:number[]): Observable<Response> {
        return this.http.post('api/products/'+productId+'/productOptions', {'optionIds':optionIds})
            .map((res: any) => this.convertResponse(res));
    }
    
    queryOptions(productId:number): Observable<Response> {
        return this.http.get('api/products/'+productId+'/productOptions')
            .map((res: any) => this.convertResponse(res));
    }

}
