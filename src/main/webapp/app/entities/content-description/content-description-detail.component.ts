import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { ContentDescription } from './content-description.model';
import { ContentDescriptionService } from './content-description.service';

@Component({
    selector: 'jhi-content-description-detail',
    templateUrl: './content-description-detail.component.html'
})
export class ContentDescriptionDetailComponent implements OnInit, OnDestroy {

    contentDescription: ContentDescription;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private contentDescriptionService: ContentDescriptionService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['contentDescription']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContentDescriptions();
    }

    load(id) {
        this.contentDescriptionService.find(id).subscribe((contentDescription) => {
            this.contentDescription = contentDescription;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.contentDescription.id !== undefined) {
            this.contentDescriptionService.update(this.contentDescription)
                .subscribe((res: ContentDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.contentDescriptionService.create(this.contentDescription)
                .subscribe((res: ContentDescription) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: ContentDescription) {
        this.eventManager.broadcast({ name: 'contentDescriptionModification', content: 'OK'});
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

    registerChangeInContentDescriptions() {
        this.eventSubscriber = this.eventManager.subscribe('contentDescriptionListModification', (response) => this.load(this.contentDescription.id));
    }
}
