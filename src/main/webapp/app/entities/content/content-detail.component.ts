import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , AlertService } from 'ng-jhipster';

import { Content } from './content.model';
import { ContentService } from './content.service';

@Component({
    selector: 'jhi-content-detail',
    templateUrl: './content-detail.component.html'
})
export class ContentDetailComponent implements OnInit, OnDestroy {

    content: Content;
    private subscription: any;
    private eventSubscriber: Subscription;
    isSaving: boolean;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private contentService: ContentService,
        private route: ActivatedRoute,
        private alertService: AlertService
    ) {
        this.jhiLanguageService.setLocations(['content', 'contentType', 'contentPosition']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInContents();
    }

    load(id) {
        this.contentService.find(id).subscribe((content) => {
            this.content = content;
        });
    }
    previousState() {
        window.history.back();
    }
    save() {
        this.isSaving = true;
        if (this.content.id !== undefined) {
            this.contentService.update(this.content)
                .subscribe((res: Content) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        } else {
            this.contentService.create(this.content)
                .subscribe((res: Content) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
        }
    }

    private onSaveSuccess(result: Content) {
        this.eventManager.broadcast({ name: 'contentModification', content: 'OK'});
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

    registerChangeInContents() {
        this.eventSubscriber = this.eventManager.subscribe('contentListModification', (response) => this.load(this.content.id));
    }
}
