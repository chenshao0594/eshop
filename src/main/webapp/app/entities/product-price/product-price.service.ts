import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams, BaseRequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ProductPrice } from './product-price.model';
import { DateUtils } from 'ng-jhipster';
@Injectable()
export class ProductPriceService {

    private resourceUrl = 'api/product-prices';
    private resourceSearchUrl = 'api/product-prices/_search';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(productPrice: ProductPrice): Observable<ProductPrice> {
        const copy: ProductPrice = Object.assign({}, productPrice);
        copy.productPriceSpecialEndDate = this.dateUtils
            .convertLocalDateToServer(productPrice.productPriceSpecialEndDate);
        copy.productPriceSpecialStartDate = this.dateUtils
            .convertLocalDateToServer(productPrice.productPriceSpecialStartDate);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(productPrice: ProductPrice): Observable<ProductPrice> {
        const copy: ProductPrice = Object.assign({}, productPrice);
        copy.productPriceSpecialEndDate = this.dateUtils
            .convertLocalDateToServer(productPrice.productPriceSpecialEndDate);
        copy.productPriceSpecialStartDate = this.dateUtils
            .convertLocalDateToServer(productPrice.productPriceSpecialStartDate);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ProductPrice> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            jsonResponse.productPriceSpecialEndDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.productPriceSpecialEndDate);
            jsonResponse.productPriceSpecialStartDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse.productPriceSpecialStartDate);
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
            jsonResponse[i].productPriceSpecialEndDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].productPriceSpecialEndDate);
            jsonResponse[i].productPriceSpecialStartDate = this.dateUtils
                .convertLocalDateFromServer(jsonResponse[i].productPriceSpecialStartDate);
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
