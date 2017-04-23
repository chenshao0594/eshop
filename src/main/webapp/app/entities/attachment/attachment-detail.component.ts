import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager , JhiLanguageService , DataUtils } from 'ng-jhipster';

import { Attachment } from './attachment.model';
import { AttachmentService } from './attachment.service';

@Component({
    selector: 'jhi-attachment-detail',
    templateUrl: './attachment-detail.component.html'
})
export class AttachmentDetailComponent implements OnInit, OnDestroy {

    attachment: Attachment;
    private subscription: any;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private jhiLanguageService: JhiLanguageService,
        private dataUtils: DataUtils,
        private attachmentService: AttachmentService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['attachment']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAttachments();
    }

    load(id) {
        this.attachmentService.find(id).subscribe((attachment) => {
            this.attachment = attachment;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAttachments() {
        this.eventSubscriber = this.eventManager.subscribe('attachmentListModification', (response) => this.load(this.attachment.id));
    }
}
