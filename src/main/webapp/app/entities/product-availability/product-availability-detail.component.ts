import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ProductAvailability } from './product-availability.model';
import { ProductAvailabilityService } from './product-availability.service';

@Component({
    selector: 'jhi-product-availability-detail',
    templateUrl: './product-availability-detail.component.html'
})
export class ProductAvailabilityDetailComponent implements OnInit, OnDestroy {

    productAvailability: ProductAvailability;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private productAvailabilityService: ProductAvailabilityService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['productAvailability']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProductAvailabilities();
    }

    load(id) {
        this.productAvailabilityService.find(id).subscribe((productAvailability) => {
            this.productAvailability = productAvailability;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.productAvailability.id !== undefined) {
            this.productAvailabilityService.update(this.productAvailability)
                .subscribe((res: ProductAvailability) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.productAvailabilityService.create(this.productAvailability)
                .subscribe((res: ProductAvailability) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ProductAvailability) {
        this.eventManager.broadcast({ name: 'productAvailabilityModification', content: 'OK'});
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

    registerChangeInProductAvailabilities() {
        this.eventSubscriber = this.eventManager.subscribe('productAvailabilityListModification', (response) => this.load(this.productAvailability.id));
    }
}
