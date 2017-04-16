import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductPriceDescription } from './product-price-description.model';
import { ProductPriceDescriptionService } from './product-price-description.service';

@Component({
    selector: 'jhi-product-price-description-detail',
    templateUrl: './product-price-description-detail.component.html'
})
export class ProductPriceDescriptionDetailComponent implements OnInit, OnDestroy {

    productPriceDescription: ProductPriceDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productPriceDescriptionService: ProductPriceDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productPriceDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductPriceDescriptions();
    }

    load(id) {
        this.productPriceDescriptionService.find(id).subscribe((productPriceDescription) => {
            this.productPriceDescription = productPriceDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productPriceDescription.id !== undefined) {
            this.productPriceDescriptionService.update(this.productPriceDescription)
                .subscribe((res: ProductPriceDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productPriceDescriptionService.create(this.productPriceDescription)
                .subscribe((res: ProductPriceDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductPriceDescription) {
        this.eventManager.broadcast({ name: 'productPriceDescriptionModification', content: 'OK'});
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

    registerChangeInProductPriceDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('productPriceDescriptionListModification', (response) => this.load(this.productPriceDescription.id));
    }
}
