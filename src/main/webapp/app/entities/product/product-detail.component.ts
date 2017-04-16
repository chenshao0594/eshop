import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { Product } from './product.model';
import { ProductService } from './product.service';

@Component({
    selector: 'jhi-product-detail',
    templateUrl: './product-detail.component.html'
})
export class ProductDetailComponent implements OnInit, OnDestroy {

    product: Product;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productService: ProductService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['product']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProducts();
    }

    load(id) {
        this.productService.find(id).subscribe((product) => {
            this.product = product;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.product.id !== undefined) {
            this.productService.update(this.product)
                .subscribe((res: Product) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productService.create(this.product)
                .subscribe((res: Product) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Product) {
        this.eventManager.broadcast({ name: 'productModification', content: 'OK'});
        this.isSaving = false;
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProducts() {
        this.eventSubscriber = this.eventManager.subscribe('productListModification', (response) => this.load(this.product.id));
    }
}
