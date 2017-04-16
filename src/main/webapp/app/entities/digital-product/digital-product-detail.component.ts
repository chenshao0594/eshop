import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { DigitalProduct } from './digital-product.model';
import { DigitalProductService } from './digital-product.service';

@Component({
    selector: 'jhi-digital-product-detail',
    templateUrl: './digital-product-detail.component.html'
})
export class DigitalProductDetailComponent implements OnInit, OnDestroy {

    digitalProduct: DigitalProduct;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private digitalProductService: DigitalProductService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['digitalProduct']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDigitalProducts();
    }

    load(id) {
        this.digitalProductService.find(id).subscribe((digitalProduct) => {
            this.digitalProduct = digitalProduct;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.digitalProduct.id !== undefined) {
            this.digitalProductService.update(this.digitalProduct)
                .subscribe((res: DigitalProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.digitalProductService.create(this.digitalProduct)
                .subscribe((res: DigitalProduct) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: DigitalProduct) {
        this.eventManager.broadcast({ name: 'digitalProductModification', content: 'OK'});
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

    registerChangeInDigitalProducts() {
        this.eventSubscriber = this.eventManager.subscribe('digitalProductListModification', (response) => this.load(this.digitalProduct.id));
    }
}
