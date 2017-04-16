import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductOptionValue } from './product-option-value.model';
import { ProductOptionValueService } from './product-option-value.service';

@Component({
    selector: 'jhi-product-option-value-detail',
    templateUrl: './product-option-value-detail.component.html'
})
export class ProductOptionValueDetailComponent implements OnInit, OnDestroy {

    productOptionValue: ProductOptionValue;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productOptionValueService: ProductOptionValueService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productOptionValue']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductOptionValues();
    }

    load(id) {
        this.productOptionValueService.find(id).subscribe((productOptionValue) => {
            this.productOptionValue = productOptionValue;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productOptionValue.id !== undefined) {
            this.productOptionValueService.update(this.productOptionValue)
                .subscribe((res: ProductOptionValue) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productOptionValueService.create(this.productOptionValue)
                .subscribe((res: ProductOptionValue) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductOptionValue) {
        this.eventManager.broadcast({ name: 'productOptionValueModification', content: 'OK'});
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

    registerChangeInProductOptionValues() {
        this.eventSubscriber = this.eventManager.subscribe('productOptionValueListModification', (response) => this.load(this.productOptionValue.id));
    }
}
