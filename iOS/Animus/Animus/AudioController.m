//
//  AudioController.m
//  Animus
//
//  Created by Bartlomiej Siemieniuk on 21/03/2015.
//  Copyright (c) 2015 TeamGoat. All rights reserved.
//

#import "AudioController.h"
#import "Streamer.h"

@interface AudioController()

@end

@implementation AudioController

static Streamer * streamer;

-(id) initWith: (Streamer *)streamerObject {
    if (self = [super init]) {
        streamer = streamerObject;
    }
    return self;
}

- (void)initRecording
{
        if (!recordState.recording) {
            
            printf("Starting recording\n");
            [self startRecording];
            
        } else {
            
            printf("Stopping recording\n");
            [self stopRecording];
        
        }
}

- (void)startRecording
{
    [self setupAudioFormat: &recordState.dataFormat];
    
    recordState.currentPacket = 0;
    
    OSStatus status;
    status = AudioQueueNewInput(&recordState.dataFormat,
                                AudioInputCallback,
                                &recordState,
                                CFRunLoopGetCurrent(),
                                kCFRunLoopCommonModes,
                                0,
                                &recordState.queue);
    
    if (status == 0)
    {
        // Prime recording buffers with empty data
        for (int i = 0; i < NUM_BUFFERS; i++)
        {
            AudioQueueAllocateBuffer(recordState.queue, 1600, &recordState.buffers[i]);
            AudioQueueEnqueueBuffer (recordState.queue, recordState.buffers[i], 0, NULL);
        }
//        
//        status = AudioFileCreateWithURL(fileURL,
//                                        kAudioFileAIFFType,
//                                        &recordState.dataFormat,
//                                        kAudioFileFlags_EraseFile,
//                                        &recordState.audioFile);

        if (status == 0)
        {
            recordState.recording = true;
            status = AudioQueueStart(recordState.queue, NULL);
            if (status == 0)
            {
                NSLog(@"Recording");
            }
        }
    }
    
    if (status != 0)
    {
        [self stopRecording];
        NSLog(@"Record Failed");
    }
}

- (void)stopRecording
{
    recordState.recording = false;
    
    AudioQueueStop(recordState.queue, true);
    for(int i = 0; i < NUM_BUFFERS; i++)
    {
        AudioQueueFreeBuffer(recordState.queue, recordState.buffers[i]);
    }
    
    AudioQueueDispose(recordState.queue, true);
    AudioFileClose(recordState.audioFile);
    NSLog(@"Idle");
}


// Takes a filled buffer and send to speech service, "emptying" the buffer
void AudioInputCallback(void * inUserData,
                        AudioQueueRef inAQ,
                        AudioQueueBufferRef inBuffer,
                        const AudioTimeStamp * inStartTime,
                        UInt32 inNumberPacketDescriptions,
                        const AudioStreamPacketDescription * inPacketDescs)
{
    RecordState * recordState = (RecordState*)inUserData;
    if (!recordState->recording)
    {
        printf("Not recording, returning\n");
    }
    
    if (true)
    {
        recordState->currentPacket += inNumberPacketDescriptions;
        
        NSUInteger length = inNumberPacketDescriptions * 2;
        NSData* audioData = [NSData dataWithBytes:inBuffer->mAudioData length:length];
        NSLog(@"Buffer Size: %lu",(long unsigned)length);
        
        [AudioController handleData:audioData];
    }
    
    AudioQueueEnqueueBuffer(recordState->queue, inBuffer, 0, NULL);
}

+(void)handleData:(NSData *)data {
    [streamer send: data];
}

- (void)setupAudioFormat:(AudioStreamBasicDescription *)format
{
    format->mSampleRate = 8000.0;
    format->mFormatID   = kAudioFormatLinearPCM;
    
    format->mFramesPerPacket = 1;
    format->mChannelsPerFrame = 1;
    
    format->mBytesPerFrame = 2;
    format->mBytesPerPacket = 2;
    format->mBitsPerChannel = 16;
    
    format->mReserved = 0;
    format->mFormatFlags =
        kAudioFormatFlagIsSignedInteger;
}

@end
