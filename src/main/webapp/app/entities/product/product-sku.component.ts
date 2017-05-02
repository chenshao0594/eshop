import { Component, OnInit, OnDestroy, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { Response } from '@angular/http';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';
import { ProductService } from './product.service';
import { ProductOption } from '../product-option/product-option.model';

@Component( {
    templateUrl: './product-sku.component.html'
} )
export class ProductSkuComponent  implements OnInit {
    productId: number;
    productOptions: ProductOption[];
    private subscription: any;
    private eventSubscriber: Subscription;
    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private productService: ProductService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['product']);
        this.jhiLanguageService.setLocations(['productOption']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.productId = params['id'];
            this.loadOptions(this.productId);
            });
        this.registerChangeInProductOptions();
    }

    back() {
        window.history.back();
    }
    loadOptions(productId){
        this.productService.queryOptions(this.productId).subscribe(
                (res: Response) => { this.productOptions = res.json(); }, 
                (res: Response) => this.onError(res.json()));
        
        
    }
    
    registerChangeInProductOptions() {
      this.eventSubscriber = this.eventManager.subscribe('optionListModification', (response) => this.loadOptions(this.productId));
  }
    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
